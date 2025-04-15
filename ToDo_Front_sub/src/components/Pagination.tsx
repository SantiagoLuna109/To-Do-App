import React from "react";
import '../styles/Pagination.css';

interface PaginationProps {
  currentPage: number;
  totalPages: number;
  onPageChange: (page: number) => void;
}

const Pagination: React.FC<PaginationProps> = ({ currentPage, totalPages, onPageChange }) => {
  const handlePageChange = (page: number) => {
    if (page >= 0 && page < totalPages) {
      onPageChange(page);
    }
  };

  const getPageNumbers = (): (number | string)[] => {
    const pages: (number | string)[] = [];
    if (totalPages <= 5) {
      for (let i = 1; i <= totalPages; i++) {
        pages.push(i);
      }
    } else {
      pages.push(1);
      let start = Math.max(2, currentPage + 1 - 1);
      let end = Math.min(totalPages - 1, currentPage + 1 + 1);
      if (currentPage + 1 <= 3) {
        start = 2;
        end = 4;
      }
      else if (currentPage + 1 >= totalPages - 2) {
        start = totalPages - 3;
        end = totalPages - 1;
      }
      if (start > 2) {
        pages.push("...");
      }
      for (let i = start; i <= end; i++) {
        pages.push(i);
      }
      if (end < totalPages - 1) {
        pages.push("...");
      }
      pages.push(totalPages);
    }
    return pages;
  };

  const pages = getPageNumbers();

  return (
    <div className="pagination">
      <button disabled={currentPage === 0} onClick={() => handlePageChange(0)}>
      &lt;&lt;
      </button>
      <button disabled={currentPage === 0} onClick={() => handlePageChange(currentPage - 1)}>
      &lt;
      </button>
      {pages.map((p, index) =>
        typeof p === "number" ? (
          <button
            key={index}
            onClick={() => handlePageChange(p - 1)}
            style={{ fontWeight: currentPage === p - 1 ? "bold" : "normal" }}
          >
            {p}
          </button>
        ) : (
          <span key={index} style={{ margin: "0 5px" }}>
            {p}
          </span>
        )
      )}
      <button disabled={currentPage === totalPages - 1} onClick={() => handlePageChange(currentPage + 1)}>
      &gt;
      </button>
      <button disabled={currentPage === totalPages - 1} onClick={() => handlePageChange(totalPages - 1)}>
      &gt;&gt;
      </button>
    </div>
  );
};

export default Pagination;