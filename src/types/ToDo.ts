export type Priority = "High" | "Medium" | "Low";
export interface ToDo{
    id: number;
    name: string;
    priority: Priority;
    dueDate?: string;
    done: boolean;
    timeToFinish?: number;
}