import React from "react";

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
    <div className="metrics">
      <h3>Metrics</h3>
      <p>High Priority Average: {avgByPriority["1"] ?? 0} mins</p>
      <p>Medium Priority Average: {avgByPriority["2"] ?? 0} mins</p>
      <p>Low Priority Average: {avgByPriority["3"] ?? 0} mins</p>
      <p>Overall Average: {metrics.globalAverage ?? 0} mins</p>
      <p>Total Filtered: {metrics.totalFilteres ?? 0}</p>
    </div>
  );
};

export default Metrics;