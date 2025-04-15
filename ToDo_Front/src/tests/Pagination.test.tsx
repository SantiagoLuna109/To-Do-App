import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import Pagination from '../components/Pagination';

describe('Pagination Component', () => {
  it('renders correct fixed buttons and page numbers', () => {
    const onPageChange = jest.fn();
    render(<Pagination currentPage={4} totalPages={10} onPageChange={onPageChange} />);
    
    expect(screen.getByText('<<')).toBeInTheDocument();
    expect(screen.getByText('<')).toBeInTheDocument();
    expect(screen.getByText('>')).toBeInTheDocument();
    expect(screen.getByText('>>')).toBeInTheDocument();
    
    expect(screen.getByText('5')).toBeInTheDocument();
  });
  
  it('calls onPageChange when a specific page button is clicked', () => {
    const onPageChange = jest.fn();
    render(<Pagination currentPage={2} totalPages={5} onPageChange={onPageChange} />);
    
    const pageButton = screen.getByText('3');
    fireEvent.click(pageButton);
    expect(onPageChange).toHaveBeenCalledWith(2);
  });
  
  it('calls onPageChange when fixed buttons are clicked', () => {
    const onPageChange = jest.fn();
    render(<Pagination currentPage={3} totalPages={10} onPageChange={onPageChange} />);
    
    const firstButton = screen.getByText('<<');
    const finalButton = screen.getByText('>>');
    
    fireEvent.click(firstButton);
    fireEvent.click(finalButton);
    
    expect(onPageChange).toHaveBeenCalledWith(0);    
    expect(onPageChange).toHaveBeenCalledWith(9);       
  });
});