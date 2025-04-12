import React from "react";
import { ToDo } from "../types/Todo";

interface TodoTableProps {
    toDos: ToDo[];
    onDelete: (id: number) => void;
    onEdit: (todo: ToDo) => void;
    onSort: (key: keyof ToDo) => void;
    sortConfig: {key: keyof ToDo; direction: "asc" | "desc"} | null;
}

const TodoTable: React.FC<TodoTableProps> = ({toDos, onDelete, onEdit, onSort, sortConfig}) => {
    const getRowStyle = (dueDate?: string): React.CSSProperties => {
        if(!dueDate) return {};
        const due = new Date(dueDate);
        const today = new Date();
        const diffTime = due.getTime() - today.getTime();
        const diffDays = diffTime / (1000 * 3600 * 24);
        if(diffDays < 7){
            return { backgroundColor: "red"};
        } else if(diffDays < 14 ){
            return { backgroundColor: "yellow"};
        } else {
            return {backgroundColor: "green"};
        }
    };
    const renderSortIcon = (key: keyof ToDo) => {
        if (!sortConfig || sortConfig.key !== key) return null;
        return sortConfig.direction === "asc" ? "UP" : "DOWN"; //Buscar flechitas hacia arriba y hacia abajo,cfreo existe el emoji
    };
    return(
        <table>
            <thead>
                <tr>
                    <th>Name</th>
                    <th onClick={() => onSort("priority")}>Priority {renderSortIcon("priority")}</th>
                    <th onClick={() => onSort("dueDate")}>Due-Date {renderSortIcon("dueDate")}</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                {toDos.map((toDo) => (
                    <tr key={toDo.id} style={getRowStyle(toDo.dueDate)}>
                        <td style={{textDecoration: toDo.done ? "line-through": "none"}}>{toDo.name}</td>
                        <td>{toDo.priority}</td>
                        <td>{toDo.dueDate ? new Date(toDo.dueDate).toLocaleDateString(): - "-"}</td>
                        <td><button onClick={() => onEdit(toDo)}>Edit</button> <button onClick={() => onDelete(toDo.id)}>Delete</button></td>
                    </tr>
                ))}
            </tbody>
        </table>
    );
};
export default TodoTable;