export interface Metrics {
    total: number;                              
    overallAverage: number;                    
    averageTimesByPriority: Record<string, number>; 
}
  