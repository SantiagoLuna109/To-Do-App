import React, { useEffect, useState } from "react";
import { ToDo } from "../types/ToDo";
import "../styles/ModalTodo.css";

const toLocal = (iso?: string | null) =>
  iso ? new Date(iso).toISOString().slice(0, 16) : "";

interface Props {
  toDo: ToDo | null;
  onClose: () => void;
  onSave: (todo: Omit<ToDo, "id" | "creationDate">, id?: number) => void;
}

const ModalTodo: React.FC<Props> = ({ toDo, onClose, onSave }) => {
  const [text, setText]         = useState("");
  const [priority, setPriority] = useState(2);
  const [dueDate, setDueDate]   = useState("");
  const [doneFlag, setDoneFlag] = useState(false);

  useEffect(() => {
    if (toDo) {
      setText(toDo.text);
      setPriority(toDo.priority);
      setDueDate(toLocal(toDo.dueDate));
      setDoneFlag(toDo.doneFlag);
    } else {
      setText("");
      setPriority(2);
      setDueDate("");
      setDoneFlag(false);
    }
  }, [toDo]);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSave(
      {
        text,
        priority,
        dueDate:   dueDate ? new Date(dueDate).toISOString() : null,
        doneFlag,
        doneDate:  doneFlag ? new Date().toISOString() : null,
      },
      toDo?.id
    );
  };

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal" onClick={e => e.stopPropagation()}>
        <h2>{toDo ? "Edit To-Do" : "New To-Do"}</h2>

        <form onSubmit={handleSubmit}>
          <label>
            Text
            <input
              autoFocus
              required
              maxLength={120}
              value={text}
              onChange={e => setText(e.target.value)}
            />
          </label>

          <label>
            Priority
            <select
              value={priority}
              onChange={e => setPriority(Number(e.target.value))}
            >
              <option value={1}>High</option>
              <option value={2}>Medium</option>
              <option value={3}>Low</option>
            </select>
          </label>

          <label>
            Due date
            <input
              type="datetime-local"
              value={dueDate}
              onChange={e => setDueDate(e.target.value)}
            />
          </label>

          <label className="done-check">
            <input
              type="checkbox"
              checked={doneFlag}
              onChange={e => setDoneFlag(e.target.checked)}
            />
            Mark as done
          </label>

          <div className="modal-actions">
            <button type="submit">{toDo ? "Save" : "Create"}</button>
            <button type="button" onClick={onClose}>
              Cancel
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default ModalTodo;
