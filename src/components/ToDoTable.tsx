import React from "react";
import { ToDo } from "../types/Todo";

interface TodoTableProps {
    toDos: ToDo[];
    onDelete: (id: number) => void;
    onEdit: (todo: ToDo) => void;
    onSort: (key: keyof ToDo) => void;
    sortConfig: {key: keyof ToDo; direction: "asc" | "desc"} | null;
    onMarkDone: (toDo: ToDo) => void;
}

const TodoTable: React.FC<TodoTableProps> = ({
    toDos,
    onDelete,
    onEdit,
    onSort,
    sortConfig,
    onMarkDone,
  }) => {
    const getRowStyle = (dueDate: string | null): React.CSSProperties => {
      if (!dueDate) return {};
      const due = new Date(dueDate);
      const today = new Date();
      const diffTime = due.getTime() - today.getTime();
      const diffDays = diffTime / (1000 * 3600 * 24);
      if (diffDays < 7) {
        return { backgroundColor: "red" };
      } else if (diffDays < 14) {
        return { backgroundColor: "yellow" };
      } else {
        return { backgroundColor: "green" };
      }
    };
  
    const renderSortIcon = (key: keyof ToDo) => {
      if (!sortConfig || sortConfig.key !== key) return null;
      return sortConfig.direction === "asc" ? "↑" : "↓";
    };
  
    return (
      <table>
        <thead>
          <tr>
            <th>Done</th>
            <th>Text</th>
            <th onClick={() => onSort("priority")}>
              Priority {renderSortIcon("priority")}
            </th>
            <th onClick={() => onSort("dueDate")}>
              Due Date {renderSortIcon("dueDate")}
            </th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {toDos.map((toDo) => (
            <tr key={toDo.id} style={getRowStyle(toDo.dueDate ?? null)}>
              <td>
                <button onClick={() => onMarkDone(toDo)}>
                    {toDo.doneFlag ? "" : "✓"}
                </button>
              </td>
              <td style={{ textDecoration: toDo.doneFlag ? "line-through" : "none" }}>
                {toDo.text}
              </td>
              <td>
                {toDo.priority === 1
                  ? "High"
                  : toDo.priority === 2
                  ? "Medium"
                  : "Low"}
              </td>
              <td>{toDo.dueDate ? new Date(toDo.dueDate).toLocaleString() : "-"}</td>
              <td>
                <button onClick={() => onEdit(toDo)}>Edit</button>
                <button onClick={() => onDelete(toDo.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    );
  };
  
  export default TodoTable;