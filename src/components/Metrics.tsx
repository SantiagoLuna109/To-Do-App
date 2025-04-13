import React, { useMemo } from "react";
import { ToDo } from "../types/Todo";

interface MetricsProps {
  toDos: ToDo[];
}

const Metrics: React.FC<MetricsProps> = ({ toDos }) => {
  const computeTimeDiff = (creationDate: string, doneDate: string): number => {
    const start = new Date(creationDate);
    const end = new Date(doneDate);
    return Math.round((end.getTime() - start.getTime()) / (1000 * 60));
  };

  const averageTimes = useMemo(() => {
    const priorities = [1, 2, 3];
    const averages: { [priority: number]: number } = {};
    priorities.forEach((p) => {
      const tasks = toDos.filter(
        (toDo) =>
          toDo.priority === p &&
          toDo.doneFlag &&
          toDo.creationDate &&
          toDo.doneDate
      );
      const totalTime = tasks.reduce((sum, toDo) => {
        return sum + computeTimeDiff(toDo.creationDate, toDo.doneDate!);
      }, 0);
      averages[p] = tasks.length ? Math.round(totalTime / tasks.length) : 0;
    });
    return averages;
  }, [toDos]);

  const overallAverage = useMemo(() => {
    const sum = (averageTimes[1] || 0) + (averageTimes[2] || 0) + (averageTimes[3] || 0);
    return Math.round(sum / 3);
  }, [averageTimes]);

  return (
    <div className="metrics">
      <h3>Metrics</h3>
      <p>High Priority Average: {averageTimes[1]} mins</p>
      <p>Medium Priority Average: {averageTimes[2]} mins</p>
      <p>Low Priority Average: {averageTimes[3]} mins</p>
      <p>Overall Average: {overallAverage} mins</p>
    </div>
  );
};

export default Metrics;