// src/tests/Metrics.test.tsx
import React from 'react';
import { render, screen } from '@testing-library/react';
import Metrics from '../components/Metrics';

describe('Metrics Component', () => {
  const sampleMetrics = {
    globalAverage: 10,
    averageTimesByPriority: { "1": 5, "2": 15, "3": 20 },
    totalFilteres: 30,
  };

  it('renders metrics values correctly', () => {
    render(<Metrics metrics={sampleMetrics} />);
    
    expect(screen.getByText(/High Priority Average:/i)).toHaveTextContent('High Priority Average: 5 mins');
    expect(screen.getByText(/Medium Priority Average:/i)).toHaveTextContent('Medium Priority Average: 15 mins');
    expect(screen.getByText(/Low Priority Average:/i)).toHaveTextContent('Low Priority Average: 20 mins');
    expect(screen.getByText(/Overall Average:/i)).toHaveTextContent('Overall Average: 10 mins');
    expect(screen.getByText(/Total Filtered:/i)).toHaveTextContent('Total Filtered: 30');
  });
});