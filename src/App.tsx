import React, { useEffect, useState } from "react";
import { ToDo } from "./types/Todo";
import { PageResponse } from "./types/PageResponse";
import { fetchToDos, createToDo, updateToDo, deleteToDo } from "./services/API";
import Filters from "./components/Filters";
import TodoTable from "./components/ToDoTable";
import ModalTodo from "./components/ModalTodo";
import Pagination from "./components/Pagination";
import Metrics from "./components/Metrics";

const App: React.FC = () => {
  const [todosPage, setTodosPage] = useState<PageResponse<ToDo> | null>(null);
  const [todos, setTodos] = useState<ToDo[]>([]);
  const [searchTerm, setSearchTerm] = useState<string>("");
  const [selectedPriority, setSelectedPriority] = useState<string>("All");
  const [selectedState, setSelectedState] = useState<string>("All");
  const [sortConfig, setSortConfig] = useState<{ key: keyof ToDo; direction: "asc" | "desc" } | null>(null);
  const [currentPage, setCurrentPage] = useState<number>(0);
  const pageSize = 10;
  const [modalVisible, setModalVisible] = useState<boolean>(false);
  const [editingTodo, setEditingTodo] = useState<ToDo | null>(null);
  const [metrics, setMetrics] = useState<any>({});

  const loadTodos = async () => {
    try {
      const data = await fetchToDos(
        currentPage,
        pageSize,
        searchTerm,
        selectedPriority,
        selectedState,
        sortConfig ? String(sortConfig.key) : "id",
        sortConfig ? sortConfig.direction : "asc"
      );
      setTodos(data.pageResponse.content);
      setTodosPage(data.pageResponse);
      setMetrics(data.metrics);
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    loadTodos();
  }, [currentPage, searchTerm, selectedPriority, selectedState, sortConfig]);

  const handleDelete = async (id: number) => {
    const confirmed = window.confirm("Do you want to delete the ToDo?");
    if(confirmed){
      try {
        await deleteToDo(id);
        await loadTodos();
      } catch (error) {
        console.error(error);
      }
    }
  };

  const handleEdit = (todo: ToDo) => {
    setEditingTodo(todo);
    setModalVisible(true);
  };

  const handleSave = async (
    toDoData: Omit<ToDo, "id" | "creationDate">,
    id?: number
  ) => {
    try {
      if (id !== undefined) {
        const updatedData = {
          text: toDoData.text,
          dueDate: toDoData.dueDate !== undefined ? toDoData.dueDate : null,
          doneFlag: toDoData.doneFlag,
          doneDate: toDoData.doneDate !== undefined ? toDoData.doneDate : null,
          priority: toDoData.priority,
        };
        await updateToDo(updatedData, id);
        await loadTodos();
      } else {
        await createToDo(toDoData);
        await loadTodos();
      }
      setModalVisible(false);
      setEditingTodo(null);
    } catch (error) {
      console.error(error);
    }
  };
  
  const handleToggleDone = async (toDo: ToDo) => {
    try {
      const updatedData = {
        text: toDo.text,
        dueDate: toDo.dueDate,
        doneFlag: !toDo.doneFlag,
        doneDate: !toDo.doneFlag ? new Date().toISOString() : null,
        priority: toDo.priority,
      };
      await updateToDo(updatedData, toDo.id);
      await loadTodos();
    } catch (error) {
      console.error(error);
    }
  };

  const handleSort = (key: keyof ToDo) => {
    let direction: "asc" | "desc" = "asc";
    if (sortConfig && sortConfig.key === key && sortConfig.direction === "asc") {
      direction = "desc";
    }
    setSortConfig({ key, direction });
  };

  const handleToggleAll = async (done: boolean) => {
    try {
      const updatePromises = todos.map(async (toDo) => {
        if (toDo.doneFlag !== done) {
          const updatedData = {
            text: toDo.text,
            dueDate: toDo.dueDate,
            doneFlag: done,
            doneDate: done ? new Date().toISOString() : null,
            priority: toDo.priority,
          };
          return await updateToDo(updatedData, toDo.id);
        }
        return null;
      });
      await Promise.all(updatePromises);
      await loadTodos();
    } catch (error) {
      console.error(error);
    }
  };
  const handleStateChange = (value:string) => {
    setSelectedState(value);
    setCurrentPage(0);
  }

  const totalPages = todosPage ? todosPage.totalPage : 0;

  return (
    <div className="container">
      <h1>To-Do App</h1>
      <Filters
        searchTerm={searchTerm}
        onSearchChange={setSearchTerm}
        selectedPriority={selectedPriority}
        onPriorityChange={setSelectedPriority}
        selectedState={selectedState}
        onStateChange={handleStateChange}
      />
      <button onClick={() => setModalVisible(true)}>New To Do</button>
      <TodoTable
        toDos={todos}
        onDelete={handleDelete}
        onEdit={handleEdit}
        onSort={handleSort}
        sortConfig={sortConfig}
        onMarkDone={handleToggleDone}
        onToggleAll={handleToggleAll}
      />
      <Pagination
        currentPage={currentPage}
        totalPages={totalPages}
        onPageChange={(page) => setCurrentPage(page)}
      />
      <Metrics metrics={metrics} />
      {modalVisible && (
        <ModalTodo
          toDo={editingTodo}
          onClose={() => {
            setModalVisible(false);
            setEditingTodo(null);
          }}
          onSave={handleSave}
        />
      )}
    </div>
  );
};

export default App;