package com.example.v0.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data @NoArgsConstructor @AllArgsConstructor
public class MetricsDTO {
    private long   total;
    private double overallAverage;
    private Map<Integer, Double> averageTimesByPriority;
}

