import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom';
import Filters from '../components/Filters';


describe('Filters Component', () => {
  it('calls onSearchChange, onPriorityChange and onStateChange with local values on Search click', () => {
    const onSearchChange = jest.fn();
    const onPriorityChange = jest.fn();
    const onStateChange = jest.fn();

    render(
      <Filters
        searchTerm=""
        onSearchChange={onSearchChange}
        selectedPriority="All"
        onPriorityChange={onPriorityChange}
        selectedState="All"
        onStateChange={onStateChange}
      />
    );

    const searchInput = screen.getByPlaceholderText("Search name");
    fireEvent.change(searchInput, { target: { value: "Test Task" } });

    const prioritySelect = screen.getByDisplayValue("All the Priorities");
    fireEvent.change(prioritySelect, { target: { value: "1" } });
    
    const stateSelect = screen.getByDisplayValue("All");
    fireEvent.change(stateSelect, { target: { value: "Done" } });

    const searchButton = screen.getByText("Search");
    fireEvent.click(searchButton);

    expect(onSearchChange).toHaveBeenCalledWith("Test Task");
    expect(onPriorityChange).toHaveBeenCalledWith("1");
    expect(onStateChange).toHaveBeenCalledWith("Done");
  });

  it('resets filters on Clear Filters click', () => {
    const onSearchChange = jest.fn();
    const onPriorityChange = jest.fn();
    const onStateChange = jest.fn();

    render(
      <Filters
        searchTerm="Initial"
        onSearchChange={onSearchChange}
        selectedPriority="1"
        onPriorityChange={onPriorityChange}
        selectedState="Done"
        onStateChange={onStateChange}
      />
    );

    const searchInput = screen.getByPlaceholderText("Search name");
    expect(searchInput).toHaveValue("Initial");
    const clearButton = screen.getByText("Reset Filters");
    fireEvent.click(clearButton);
    
    expect(onSearchChange).toHaveBeenCalledWith("");
    expect(onPriorityChange).toHaveBeenCalledWith("All");
    expect(onStateChange).toHaveBeenCalledWith("All");
  });
});