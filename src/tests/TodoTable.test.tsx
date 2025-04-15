// src/tests/TodoTable.test.tsx
import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import TodoTable from "../components/ToDoTable";
import { ToDo } from "../types/ToDo";

describe("TodoTable Component", () => {
  const sampleToDos: ToDo[] = [
    { id: 1, text: "Task One", dueDate: "2025-04-01T00:00:00", doneFlag: false, doneDate: null, priority: 1, creationDate: "2025-03-01T00:00:00" },
    { id: 2, text: "Task Two", dueDate: "2025-04-02T00:00:00", doneFlag: true, doneDate: "2025-03-15T00:00:00", priority: 2, creationDate: "2025-03-01T00:00:00" },
    { id: 3, text: "Task Three", dueDate: "2025-04-03T00:00:00", doneFlag: false, doneDate: null, priority: 3, creationDate: "2025-03-01T00:00:00" },
  ];
  
  const onDelete = jest.fn();
  const onEdit = jest.fn();
  const onSort = jest.fn();
  const onMarkDone = jest.fn();
  const onToggleAll = jest.fn();

  beforeEach(() => {
    jest.clearAllMocks();
  });

  it("renders ToDos correctly", () => {
    render(
      <TodoTable
        toDos={sampleToDos}
        onDelete={onDelete}
        onEdit={onEdit}
        onSort={onSort}
        sortConfig={null}
        onMarkDone={onMarkDone}
        onToggleAll={onToggleAll}
      />
    );
    expect(screen.getByText("Task One")).toBeInTheDocument();
    expect(screen.getByText("Task Two")).toBeInTheDocument();
    expect(screen.getByText("Task Three")).toBeInTheDocument();
  });

  it("calls onToggleAll when the header checkbox is clicked", () => {
    render(
      <TodoTable
        toDos={sampleToDos}
        onDelete={onDelete}
        onEdit={onEdit}
        onSort={onSort}
        sortConfig={null}
        onMarkDone={onMarkDone}
        onToggleAll={onToggleAll}
      />
    );
    const checkboxes = screen.getAllByRole("checkbox");
    const headerCheckbox = checkboxes[0];
    fireEvent.click(headerCheckbox);
    expect(onToggleAll).toHaveBeenCalledTimes(1);
  });

  it("calls onEdit when clicking the Edit button on a row", () => {
    render(
      <TodoTable
        toDos={sampleToDos}
        onDelete={onDelete}
        onEdit={onEdit}
        onSort={onSort}
        sortConfig={null}
        onMarkDone={onMarkDone}
        onToggleAll={onToggleAll}
      />
    );
    const editButtons = screen.getAllByText("Edit");
    fireEvent.click(editButtons[0]);
    expect(onEdit).toHaveBeenCalledWith(sampleToDos[0]);
  });

  it("calls onDelete when clicking the Delete button on a row", () => {
    render(
      <TodoTable
        toDos={sampleToDos}
        onDelete={onDelete}
        onEdit={onEdit}
        onSort={onSort}
        sortConfig={null}
        onMarkDone={onMarkDone}
        onToggleAll={onToggleAll}
      />
    );
    const deleteButtons = screen.getAllByText("Delete");
    fireEvent.click(deleteButtons[1]);
    expect(onDelete).toHaveBeenCalledWith(sampleToDos[1].id);
  });
});