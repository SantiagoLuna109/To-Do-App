import React, { useCallback, useEffect, useState } from "react";
import { ToDo } from "./types/ToDo";
import {
  fetchToDos,
  createToDo,
  updateToDo,
  deleteToDo,
  fetchMetrics
} from "./services/API";
import Filters    from "./components/Filters";
import TodoTable  from "./components/ToDoTable";
import ModalTodo  from "./components/ModalTodo";
import Pagination from "./components/Pagination";
import Metrics    from "./components/Metrics";
import "./styles/App.css";

const pageSize = 10;                          

const App: React.FC = () => {
  const [todos,   setTodos]   = useState<ToDo[]>([]);
  const [pages,   setPages]   = useState<number>(0);      
  const [metrics, setMetrics] = useState<any>({});

  const [searchTerm,       setSearchTerm]       = useState("");
  const [selectedPriority, setSelectedPriority] = useState("All");
  const [selectedState,    setSelectedState]    = useState("All");
  const [sortConfig,       setSortConfig]       = useState<
    { key: keyof ToDo; direction: "asc" | "desc" } | null
  >(null);
  const [currentPage, setCurrentPage] = useState(0);

  const [modalVisible, setModalVisible] = useState(false);
  const [editingTodo,  setEditingTodo]  = useState<ToDo | null>(null);

  const loadTodos = useCallback(async () => {
    const [page, m] = await Promise.all([
      fetchToDos(
        currentPage,
        pageSize,
        searchTerm,
        selectedPriority,
        selectedState,
        sortConfig ? String(sortConfig.key) : "id",
        sortConfig ? sortConfig.direction   : "asc"
      ),
      fetchMetrics(searchTerm, selectedPriority, selectedState)
    ]);
  
    setTodos(page.content);
    setPages(page.totalPages);
  
    setMetrics({
      globalAverage: m.overallAverage,
      averageTimesByPriority: m.averageTimesByPriority,
      totalFilteres: m.total            
    });
  }, [
    currentPage,
    pageSize,
    searchTerm,
    selectedPriority,
    selectedState,
    sortConfig,
  ]);

  useEffect(() => { loadTodos(); }, [loadTodos]);

  const handleDelete = async (id: number) => {
    if (window.confirm("Delete this To-Do?")) {
      await deleteToDo(id);
      await loadTodos();
    }
  };

  const handleSave = async (
    data: Omit<ToDo, "id" | "creationDate">,
    id?: number
  ) => {
    if (id !== undefined) {
      await updateToDo(data, id);
    } else {
      await createToDo(data);
    }
    await loadTodos();
    setModalVisible(false);
    setEditingTodo(null);
  };

  const handleToggleDone = async (t: ToDo) => {
    await updateToDo(
      { ...t, doneFlag: !t.doneFlag, doneDate: !t.doneFlag ? new Date().toISOString() : null },
      t.id
    );
    await loadTodos();
  };

  const handleToggleAll = async (done: boolean) => {
    await Promise.all(
      todos
        .filter(t => t.doneFlag !== done)
        .map(t => updateToDo(
          { ...t, doneFlag: done, doneDate: done ? new Date().toISOString() : null },
          t.id
        ))
    );
    await loadTodos();
  };

  const handleSort = (key: keyof ToDo) => {
    const direction =
      sortConfig && sortConfig.key === key && sortConfig.direction === "asc"
        ? "desc"
        : "asc";
    setSortConfig({ key, direction });
  };
  

  return (
    <div className="container">
      <h1>To-Do App</h1>

      <Filters
        searchTerm={searchTerm}
        onSearchChange={setSearchTerm}
        selectedPriority={selectedPriority}
        onPriorityChange={setSelectedPriority}
        selectedState={selectedState}
        onStateChange={v => { setSelectedState(v); setCurrentPage(0); }}
      />

      <button className="new-btn" onClick={() => setModalVisible(true)}>
        New To-Do
      </button>

      {modalVisible && (
        <ModalTodo
          open={modalVisible}
          initial={editingTodo}
          onCancel={() => setModalVisible(false)}
          onSave={handleSave}
        />
      )}



      <TodoTable
        toDos={todos}
        onDelete={handleDelete}
        onEdit={t => { setEditingTodo(t); setModalVisible(true); }}
        onSort={handleSort}
        sortConfig={sortConfig}
        onMarkDone={handleToggleDone}
        onToggleAll={handleToggleAll}
      />

      <Pagination
        currentPage={currentPage}
        totalPages={pages}
        onPageChange={setCurrentPage}
      />

      <Metrics metrics={metrics} />
    </div>
  );
};

export default App;
