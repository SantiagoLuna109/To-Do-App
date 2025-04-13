import {
    ToDo
} from "../types/Todo.ts";
import { PageResponse } from "../types/PageResponse.ts";

const API_Url = "http://localhost:9090/todos";

export async function fetchToDos(page: number = 1, pageSize: number = 5): Promise<PageResponse<ToDo>> {
    const response = await fetch(`${API_Url}?page=${page}&pageSize=${pageSize}`);
    if(!response.ok){
        throw new Error("Error in fetch the ToDos");
    }
    const data = await response.json();
    console.log("Answer of fetchTodos ", data);
    return data;
}

export async function createToDo(ToDo:Omit<ToDo, "id" | "creationDate">): Promise<ToDo> {
    const response = await fetch(API_Url, { 
        method: "POST",
        headers: {"Content-Type":"application/json"},
        body:JSON.stringify(ToDo)
    });
    if(!response.ok){
        throw new Error("Error creating a ToDo");
    }
    return response.json();
}

export async function updateToDo(ToDo:ToDo): Promise<ToDo> {
    const response = await fetch(`${API_Url}/${ToDo.id}`, {
        method: "PUT",
        headers: {"Content-Type":"application/json"},
        body: JSON.stringify(ToDo)
    });
    if(!response.ok){
        throw new Error("Error while Updating the ToDo");
    }
    return response.json();
}

export async function deleteToDo(id:number): Promise<void> {
    const response = await fetch(`${API_Url}/${id}`, {
        method: "DELETE" 
    });
    if (!response.ok){
        throw new Error ("Error deleting a ToDo");
    }
}

