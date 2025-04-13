import React, { useEffect, useState, useMemo } from "react";
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
  const [currentPage, setCurrentPage] = useState<number>(1);
  const pageSize = 5;
  const [modalVisible, setModalVisible] = useState<boolean>(false);
  const [editingTodo, setEditingTodo] = useState<ToDo | null>(null);

  useEffect(() => {
    async function loadTodos() {
      try {
        const data = await fetchToDos(currentPage, pageSize);
        console.log("Respuesta de fetchTodos:", data);
        setTodos(data.content);
        setTodosPage(data);
      } catch (error) {
        console.error(error);
      }
    }
    loadTodos();
  }, [currentPage]);

  const filteredTodos = useMemo(() => {
    return todos.filter(todo => {
      const matchesText = todo.text.toLowerCase().includes(searchTerm.toLowerCase());
      const matchesPriority = selectedPriority === "All" || String(todo.priority) === selectedPriority;
      const matchesState =
        selectedState === "All" ||
        (selectedState === "Done" && todo.doneFlag) ||
        (selectedState === "Undone" && !todo.doneFlag);
      return matchesText && matchesPriority && matchesState;
    });
  }, [todos, searchTerm, selectedPriority, selectedState]);

  const sortedTodos = useMemo(() => {
    if (!sortConfig) return filteredTodos;
    return [...filteredTodos].sort((a, b) => {
      const aValue = (a[sortConfig.key] ?? "") as string;
      const bValue = (b[sortConfig.key] ?? "") as string;
      if (aValue === undefined || bValue === undefined) return 0;
      if (aValue > bValue) return sortConfig.direction === "asc" ? 1 : -1;
      if (aValue < bValue) return sortConfig.direction === "asc" ? -1 : 1;
      return 0;
    });
  }, [filteredTodos, sortConfig]);

  const handleDelete = async (id: number) => {
    try {
      await deleteToDo(id);
      setTodos(prev => prev.filter(todo => todo.id !== id));
    } catch (error) {
      console.error(error);
    }
  };

  const handleSave = async (toDoData: Omit<ToDo, "id" | "creationDate">, id?: number) => {
    try {
      if (id) {
        const updated = await updateToDo({ ...toDoData, id, creationDate: editingTodo!.creationDate });
        setTodos(prev => prev.map(item => (item.id === id ? updated : item)));
      } else {
        const created = await createToDo(toDoData);
        setTodos(prev => [...prev, created]);
      }
      setModalVisible(false);
      setEditingTodo(null);
    } catch (error) {
      console.error(error);
    }
  };

  const handleEdit = (todo: ToDo) => {
    setEditingTodo(todo);
    setModalVisible(true);
  };

  const handleSort = (key: keyof ToDo) => {
    let direction: "asc" | "desc" = "asc";
    if (sortConfig && sortConfig.key === key && sortConfig.direction === "asc") {
      direction = "desc";
    }
    setSortConfig({ key, direction });
  };

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
        onStateChange={setSelectedState}
      />
      <button onClick={() => setModalVisible(true)}>+ New To Do</button>
      <TodoTable
        toDos={sortedTodos}
        onDelete={handleDelete}
        onEdit={handleEdit}
        onSort={handleSort}
        sortConfig={sortConfig}
      />
      <Pagination
        currentPage={currentPage}
        totalPages={totalPages}
        onPageChange={setCurrentPage}
      />
      <Metrics toDos={todos} />
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