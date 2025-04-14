import React from "react";

interface PaginationProps {
  currentPage: number;
  totalPages: number;
  onPageChange: (page: number) => void;
}

const Pagination: React.FC<PaginationProps> = ({ currentPage, totalPages, onPageChange }) => {
  return (
    <div className="pagination">
      <button disabled={currentPage <= 0} onClick={() => onPageChange(currentPage - 1)}>
        Previous
      </button>
      {Array.from({ length: totalPages }, (_, index) => (
        <button
          key={index}
          onClick={() => onPageChange(index)}
          style={{ fontWeight: currentPage === index? "bold" : "normal" }}>
          {index + 1}
        </button>
      ))}
      <button disabled={currentPage >= totalPages - 1} onClick={() => onPageChange(currentPage + 1)}>
        Next
      </button>
    </div>
  );
};

export default Pagination;