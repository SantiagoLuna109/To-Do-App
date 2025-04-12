import React, { useState, useEffect } from "react";
import { ToDo, Priority } from "../types/Todo";

interface ModalTodoProps {
  toDo: ToDo | null;
  onClose: () => void;
  onSave: (toDo: Omit<ToDo, "id">, id?: number) => void;
}

const ModalTodo: React.FC<ModalTodoProps> = ({ toDo, onClose, onSave }) => {
  const [name, setName] = useState<string>(toDo ? toDo.name : "");
  const [priority, setPriority] = useState<Priority>(toDo ? toDo.priority : "Medium");
  const [dueDate, setDueDate] = useState<string>(toDo && toDo.dueDate ? toDo.dueDate.substring(0, 10) : "");
  const [done, setDone] = useState<boolean>(toDo ? toDo.done : false);
  const [timeToFinish, setTimeToFinish] = useState<number>(toDo && toDo.timeToFinish ? toDo.timeToFinish : 0);

  useEffect(() => {
    if (toDo) {
      setName(toDo.name);
      setPriority(toDo.priority);
      setDueDate(toDo.dueDate ? toDo.dueDate.substring(0, 10) : "");
      setDone(toDo.done);
      setTimeToFinish(toDo.timeToFinish || 0);
    } else {
      setName("");
      setPriority("Medium");
      setDueDate("");
      setDone(false);
      setTimeToFinish(0);
    }
  }, [toDo]);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSave({ name, priority, dueDate: dueDate || undefined, done, timeToFinish: timeToFinish || undefined }, toDo ? toDo.id : undefined);
  };

  return (
    <div className="modal-overlay">
      <div className="modal">
        <h2>{toDo ? "Edit To Do" : "New To Do"}</h2>
        <form onSubmit={handleSubmit}>
          <div>
            <label>Name:</label>
            <input
              type="text"
              value={name}
              onChange={(e) => setName(e.target.value)}
              required
            />
          </div>
          <div>
            <label>Priority:</label>
            <select value={priority} onChange={(e) => setPriority(e.target.value as Priority)}>
              <option value="High">High</option>
              <option value="Medium">Medium</option>
              <option value="Low">Low</option>
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
                checked={done}
                onChange={(e) => setDone(e.target.checked)}
              />
              Done
            </label>
          </div>
          <div>
            <label>Time to Finish (mins):</label>
            <input
              type="number"
              value={timeToFinish}
              onChange={(e) => setTimeToFinish(Number(e.target.value))}
            />
          </div>
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