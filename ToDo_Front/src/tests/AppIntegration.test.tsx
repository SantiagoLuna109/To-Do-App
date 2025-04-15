import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import App from '../App';
import * as API from '../services/API';

jest.mock('../services/API');

describe('App Integration Test', () => {
  const mockedFetchToDos = API.fetchToDos as jest.Mock;

  beforeEach(() => {
    mockedFetchToDos.mockReset();
  });

  it('should update the ToDos list when a filter is applied and Search is clicked', async () => {
    const mockResponse = {
      pageResponse: {
        content: [
          {
            id: 1,
            text: 'Integration Test Todo',
            dueDate: null,
            doneFlag: false,
            doneDate: null,
            priority: 2,
            creationDate: '2025-04-14T00:00:00'
          }
        ],
        currentPage: 0,
        totalElements: 1,
        totalPage: 1
      },
      metrics: {
        globalAverage: 0,
        averageTimesByPriority: { "1": 0, "2": 0, "3": 0 },
        totalFilteres: 1
      }
    };
    mockedFetchToDos.mockResolvedValueOnce(mockResponse);
    render(<App />);
    await waitFor(() => expect(mockedFetchToDos).toHaveBeenCalled());
    const searchInput = screen.getByPlaceholderText("Search name");
    fireEvent.change(searchInput, { target: { value: "Integration Test" } });
    const searchButton = screen.getByText("Search");
    fireEvent.click(searchButton);
    mockedFetchToDos.mockResolvedValueOnce(mockResponse);
    await waitFor(() => {
      expect(screen.getByText("Integration Test Todo")).toBeInTheDocument();
    });
  });
});