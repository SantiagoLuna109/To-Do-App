export type Priority = "High" | "Medium" | "Low";
export interface ToDo{
    id: number;
    text: string;
    dueDate?: string | null;
    doneFlag: boolean;
    doneDate?: string | null;
    priority: number;
    creationDate: string;
}