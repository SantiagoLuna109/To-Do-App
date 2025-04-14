import React, { useEffect, useRef } from "react";
import { ToDo } from "../types/Todo";

interface TodoTableProps {
  toDos: ToDo[];
  onDelete: (id: number) => void;
  onEdit: (todo: ToDo) => void;
  onSort: (key: keyof ToDo) => void;
  sortConfig: { key: keyof ToDo; direction: "asc" | "desc" } | null;
  onMarkDone: (toDo: ToDo) => void;
  onToggleAll: (done: boolean) => void;
}

const TodoTable: React.FC<TodoTableProps> = ({
  toDos,
  onDelete,
  onEdit,
  onSort,
  sortConfig,
  onMarkDone,
  onToggleAll,
}) => {
  const headerCheckboxRef = useRef<HTMLInputElement>(null);

  useEffect(() => {
    if (headerCheckboxRef.current) {
      const allDone = toDos.length > 0 && toDos.every(todo => todo.doneFlag);
      const noneDone = toDos.every(todo => !todo.doneFlag);
      headerCheckboxRef.current.indeterminate = !allDone && !noneDone;
    }
  }, [toDos]);

  const getRowStyle = (dueDate: string | null): React.CSSProperties => {
    if (!dueDate) return {};
    const due = new Date(dueDate);
    const today = new Date();
    const diffTime = due.getTime() - today.getTime();
    const diffDays = diffTime / (1000 * 3600 * 24);
    if (diffDays < 7) {
      return { backgroundColor: "red" };
    } else if (diffDays < 14) {
      return { backgroundColor: "#c8bd01" };
    } else {
      return { backgroundColor: "green" };
    }
  };

  const renderSortIcon = (key: keyof ToDo) => {
    if (!sortConfig || sortConfig.key !== key) return null;
    return sortConfig.direction === "asc" ? "↑" : "↓";
  };

  const allDone = toDos.length > 0 && toDos.every(todo => todo.doneFlag);

  return (
    <table>
      <thead>
        <tr>
          <th>
            <input
              type="checkbox"
              className="done-checkbox"
              ref={headerCheckboxRef}
              checked={allDone}
              onChange={e => onToggleAll(e.target.checked)}
            />
          </th>
          <th onClick={() => onSort("text")}>
            Text {renderSortIcon("text")}</th>
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
                    <input type="checkbox" name="" id="" className="done-checkbox" checked={toDo.doneFlag} onChange={() => onMarkDone(toDo)}/>
                </td>
            <td style={{ textDecoration: toDo.doneFlag ? "line-through" : "none" }}>
              {toDo.text}
            </td>
            <td>
              {toDo.priority === 1 ? "High" : toDo.priority === 2 ? "Medium" : "Low"}
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