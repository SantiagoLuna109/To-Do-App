export interface PageResponse<T> {
    content: T[];
    currentPage: number;
    totalElements: number;
    totalPage: number;
}