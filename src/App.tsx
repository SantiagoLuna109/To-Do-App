import React, { useEffect, useState, useMemo } from "react";
import { ToDo } from "./types/Todo";
import { fetchToDos, createToDo, updateToDo, deleteToDo } from "./services/API";
import Filters from "./components/Filters";
import TodoTable from "./components/ToDoTable";
import ModalTodo from "./components/ModalTodo";
import Pagination from "./components/Pagination";
import Metrics from "./components/Metrics";

const App: React.FC = () => {
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
        const data = await fetchToDos();
        setTodos(data);
      } catch (error) {
        console.error(error);
      }
    }
    loadTodos();
  }, []);

  const filteredTodos = useMemo(() => {
    return todos.filter(todo => {
      const matchesName = todo.name.toLowerCase().includes(searchTerm.toLowerCase());
      const matchesPriority = selectedPriority === "All" || todo.priority === selectedPriority;
      const matchesState =
        selectedState === "All" ||
        (selectedState === "Done" && todo.done) ||
        (selectedState === "Undone" && !todo.done);
      return matchesName && matchesPriority && matchesState;
    });
  }, [todos, searchTerm, selectedPriority, selectedState]);

  const sortedTodos = useMemo(() => {
    if (!sortConfig) return filteredTodos;
    return [...filteredTodos].sort((a, b) => {
      const aValue = a[sortConfig.key];
      const bValue = b[sortConfig.key];
      if (aValue === undefined || bValue === undefined) return 0;
      if (aValue > bValue) return sortConfig.direction === "asc" ? 1 : -1;
      if (aValue < bValue) return sortConfig.direction === "asc" ? -1 : 1;
      return 0;
    });
  }, [filteredTodos, sortConfig]);

  const totalPages = Math.ceil(sortedTodos.length / pageSize);
  const paginatedTodos = useMemo(() => {
    const startIndex = (currentPage - 1) * pageSize;
    return sortedTodos.slice(startIndex, startIndex + pageSize);
  }, [sortedTodos, currentPage, pageSize]);

  const handleDelete = async (id: number) => {
    try {
      await deleteToDo(id);
      setTodos(prev => prev.filter(todo => todo.id !== id));
    } catch (error) {
      console.error(error);
    }
  };

  const handleSave = async (todo: Omit<ToDo, "id">, id?: number) => {
    try {
      if (id) {
        const updated = await updateToDo({ ...todo, id });
        setTodos(prev =>
          prev.map(item => (item.id === id ? updated : item))
        );
      } else {
        const created = await createToDo(todo);
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
        toDos={paginatedTodos}
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