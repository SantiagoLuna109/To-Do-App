import React from "react";
import '../styles/Metrics.css'

interface MetricsData {
  globalAverage: number;
  averageTimesByPriority: {
    [key: string]: number;
  };
  totalFilteres: number;
}

interface MetricsProps {
  metrics: MetricsData;
}

const Metrics: React.FC<MetricsProps> = ({ metrics }) => {
  const avgByPriority = metrics?.averageTimesByPriority || {};
  return (
    <div className="metrics-block">
      <div className="metrics-overall">
        <h3>Overall Metrics</h3>
        <p>Overall Average: {metrics.globalAverage ?? 0} mins</p>
        <p>Total Filtered: {metrics.totalFilteres ?? 0}</p>
      </div>
      <div className="metrics-priority">
        <h3>Metrics by Priority</h3>
        <p>High Priority Average: {avgByPriority["1"] ?? 0} mins</p>
        <p>Medium Priority Average: {avgByPriority["2"] ?? 0} mins</p>
        <p>Low Priority Average: {avgByPriority["3"] ?? 0} mins</p>
      </div>
    </div>
    
  );
};

export default Metrics;