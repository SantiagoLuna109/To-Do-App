import React, { useEffect, useState } from 'react';
import { ToDo } from '../types/ToDo';
import '../styles/ModalTodo.css'

type Priority = 1 | 2 | 3;
const label: Record<Priority, string> = { 1: 'High', 2: 'Medium', 3: 'Low' };

type UpsertToDo = Omit<ToDo, 'id' | 'creationDate'> & {
  id?: number;
  creationDate?: string;
};

interface Props {
  open: boolean;
  initial?: ToDo | null;         
  onCancel: () => void;
  onSave: (todo: UpsertToDo) => void;   
}

const ModalTodo: React.FC<Props> = ({ open, initial, onCancel, onSave }) => {
  const [text,     setText]     = useState('');
  const [priority, setPriority] = useState<Priority>(2);
  const [due,      setDue]      = useState('');
  const [done,     setDone]     = useState(false);

  useEffect(() => {
    if (initial) {
      setText(initial.text);
      setPriority(initial.priority as Priority);
      setDue(initial.dueDate ?? '');
      setDone(initial.doneFlag);
    } else {
      setText(''); setPriority(2); setDue(''); setDone(false);
    }
  }, [initial]);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    const payload: UpsertToDo = {
      ...(initial ?? {}),
      text,
      priority,
      dueDate : due || null,
      doneFlag: done,
    };

    onSave(payload);
  };

  if (!open) return null;

  return (
    <div className="fixed inset-0 z-40 bg-black/50 check_modal modal-actions">
      <div className="absolute top-1/2 left-8 -translate-y-1/2 max-w-md w-full rounded-2xl bg-zinc-800 p-6 text-zinc-100 shadow-xl">
        <h2 className="mb-6 text-center text-2xl font-bold">
          {initial ? 'Edit To-Do' : 'New To-Do'}
        </h2>

        <form onSubmit={handleSubmit}>
          <div className="modal-row">
            <label htmlFor="todo-text" className="font-medium">
              Text<span className="text-red-500">*</span>
            </label>

            <input
              id="todo-text"
              type="text"
              required
              autoFocus
              value={text}
              onChange={(e) => setText(e.target.value)}
              placeholder="Describe the task…"
              className="rounded-md border border-zinc-600 bg-zinc-900 p-2"
            />

            <label htmlFor="todo-priority" className="font-medium">
              Priority
            </label>

            <select
              id="todo-priority"
              value={priority}
              onChange={(e) => setPriority(Number(e.target.value) as Priority)}
              className="rounded-md border border-zinc-600 bg-zinc-900 p-2"
            >
              <option value={1}>High</option>
              <option value={2}>Medium</option>
              <option value={3}>Low</option>
            </select>

            <label htmlFor="todo-due" className="font-medium">
              Due date&nbsp;(optional)
            </label>

            <input
              id="todo-due"
              type="datetime-local"
              value={due}
              onChange={(e) => setDue(e.target.value)}
              className="rounded-md border border-zinc-600 bg-zinc-900 p-2"
            />
            <input
              id="todo-done"
              type="checkbox"
              checked={done}
              onChange={(e) => setDone(e.target.checked)}
              className="h-4 w-4 accent-green-500"
            />
            <label htmlFor="todo-done" className="select-none">
              Mark as done
            </label>
          </div>

          <div className="modal-actions">
            <button
              type="button"
              onClick={onCancel}
              className="rounded-md px-4 py-2 text-sm hover:bg-zinc-700"
            >
              Cancel
            </button>

            <button
              type="submit"
              disabled={!text.trim()}
              className="rounded-md bg-blue-600 px-4 py-2 text-sm font-medium text-white
                        enabled:hover:bg-blue-500 disabled:opacity-40"
            >
              {initial ? 'Save' : 'Create'}
            </button>
          </div>
        </form>
        
      </div>
    </div>
  );
};

export default ModalTodo;
