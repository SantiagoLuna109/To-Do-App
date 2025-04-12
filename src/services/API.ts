import {
    ToDo
} from "../types/Todo.ts";

const API_Url = "http://localhost:9090/todos";

export async function fetchToDos(): Promise<ToDo[]> {
    const response = await fetch(API_Url);
    if(response.ok == false){
        throw new Error("Error in fetch the ToDos");
    }
    return response.json();
}

export async function createToDo(ToDo:Omit<ToDo, "id">): Promise<ToDo> {
    const response = await fetch(API_Url, { 
        method: "POST",
        headers: {"Content-Type":"applicaton/json"},
        body:JSON.stringify(ToDo)
    });
    if(response.ok == false){
        throw new Error("Error creating a ToDo");
    }
    return response.json();
}

export async function updateToDo(ToDo:ToDo): Promise<ToDo> {
    const response = await fetch('${API_Url}/${ToDo.id}', {
        method: "PUT",
        headers: {"Content-Type":"application/json"},
        body: JSON.stringify(ToDo)
    });
    if(response.ok == false){
        throw new Error("Error while Updating the ToDo");
    }
    return response.json();
}

export async function deleteToDo(id:number): Promise<void> {
    const response = await fetch('${API_Url}/${id}', {
        method: "DELETE" 
    });
    if (response.ok == false){
        throw new Error ("Error deleting a ToDo");
    }
}

