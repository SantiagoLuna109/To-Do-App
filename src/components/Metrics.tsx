import React, {useMemo} from "react";
import { ToDo } from "../types/Todo";

interface MetricsProps {
    toDos: ToDo[];
}

const Metrics: React.FC<MetricsProps> = ({toDos}) => {
    const averageTime = useMemo(() => {
        const times = toDos.filter(toDo => toDo.timeToFinish).map(toDo => toDo.timeToFinish) as number[];
        if(times.length === 0){
            return 0;
        }
        const total = times.reduce((acc,curr) => acc = curr, 0);
        return Math.round(total / times.length);
    }, [toDos]);
    return (
        <div className="Metrics">
            <h3>Metricss</h3>
            <p>Average Time to Finish; {averageTime} mins</p>
        </div>
    );
};
export default Metrics;