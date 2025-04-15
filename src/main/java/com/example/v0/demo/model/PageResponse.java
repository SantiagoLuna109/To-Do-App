package com.example.v0.demo.model;

import java.util.List;

public class PageResponse<T> {
    private List<T> content;
    private int currentPage;
    private long totalElements;
    private int totalPage;

    public PageResponse(List<T> content, int currentPage, int totalPage, long totalElements){
        this.content=content;
        this.currentPage=currentPage;
        this.totalPage=totalPage;
        this.totalElements=totalElements;
    }
    public List<T> getContent(){
        return content;
    }
    public int getCurrentPage(){
        return currentPage;
    }
    public long getTotalElements(){
        return totalElements;
    }
    public int getTotalPage(){
        return totalPage;
    }
}
