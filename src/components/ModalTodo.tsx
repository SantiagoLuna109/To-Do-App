import React, { useState, useEffect } from "react";
import { ToDo } from "../types/Todo";

interface ModalTodoProps {
  toDo: ToDo | null;
  onClose: () => void;
  onSave: (todo: Omit<ToDo, "id" | "creationDate">, id?: number) => void;
}

const ModalTodo: React.FC<ModalTodoProps> = ({ toDo, onClose, onSave }) => {
  const [text, setText] = useState<string>(toDo ? toDo.text : "");
  const [priority, setPriority] = useState<number>(toDo ? toDo.priority : 2);
  const [dueDate, setDueDate] = useState<string>(toDo && toDo.dueDate ? toDo.dueDate.substring(0, 10) : "");
  const [doneFlag, setDoneFlag] = useState<boolean>(toDo ? toDo.doneFlag : false);
  const [doneDate, setDoneDate] = useState<string>(toDo && toDo.doneDate ? toDo.doneDate.substring(0, 10) : "");

  useEffect(() => {
    if (toDo) {
      setText(toDo.text);
      setPriority(toDo.priority);
      setDueDate(toDo.dueDate ? toDo.dueDate.substring(0, 10) : "");
      setDoneFlag(toDo.doneFlag);
      setDoneDate(toDo.doneDate ? toDo.doneDate.substring(0, 10) : "");
    } else {
      setText("");
      setPriority(2);
      setDueDate("");
      setDoneFlag(false);
      setDoneDate("");
    }
  }, [toDo]);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    const payload = {
      text,
      dueDate: dueDate || null,
      doneFlag,
      doneDate: doneFlag && doneDate ? doneDate : null,
      priority,
    };
    console.log("Enviando payload:", payload);
    onSave(payload, toDo ? toDo.id : undefined);
  };

  return (
    <div className="modal-overlay">
      <div className="modal">
        <h2>{toDo ? "Edit To Do" : "New To Do"}</h2>
        <form onSubmit={handleSubmit}>
          <div>
            <label>Text:</label>
            <input
              type="text"
              value={text}
              onChange={(e) => setText(e.target.value)}
              required
            />
          </div>
          <div>
            <label>Priority:</label>
            <select value={priority} onChange={(e) => setPriority(Number(e.target.value))}>
              <option value={1}>High</option>
              <option value={2}>Medium</option>
              <option value={3}>Low</option>
            </select>
          </div>
          <div>
            <label>Due Date:</label>
            <input
              type="date"
              value={dueDate}
              onChange={(e) => setDueDate(e.target.value)}
            />
          </div>
          <div>
            <label>
              <input
                type="checkbox"
                checked={doneFlag}
                onChange={(e) => setDoneFlag(e.target.checked)}
              />
              Done
            </label>
          </div>
          {doneFlag && (
            <div>
              <label>Done Date:</label>
              <input
                type="date"
                value={doneDate}
                onChange={(e) => setDoneDate(e.target.value)}
                required={doneFlag}
              />
            </div>
          )}
          <div className="modal-actions">
            <button type="submit">{toDo ? "Save Changes" : "Create"}</button>
            <button type="button" onClick={onClose}>Cancel</button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default ModalTodo;