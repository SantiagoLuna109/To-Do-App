package com.example.v0.demo.model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
public class ToDosResponse {
    private PageResponse<ToDo> pageResponse;
    private Map<String, Object> metrics;
    public ToDosResponse(PageResponse<ToDo> pageResponse, Map<String, Object> metrics){
        this.pageResponse = pageResponse;
        this.metrics= metrics;
    }

}
