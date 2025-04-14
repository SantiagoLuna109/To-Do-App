import {
    ToDo
} from "../types/Todo.ts";

const API_Url = "http://localhost:9090/todos";

export async function fetchToDos(
  page: number,
  size: number,
  searchTerm: string,
  selectedPriority: string,
  selectedState: string,
  sortField: string,
  sortDir: string
) {
  const params = new URLSearchParams();
  params.append("page", String(page));
  params.append("size", String(size));
  if (selectedState === "Done") {
    params.append("done", "true");
  } else if (selectedState === "Undone") {
    params.append("done", "false");
  }
  if (searchTerm) {
    params.append("name", searchTerm);
  }
  if (selectedPriority !== "All") {
    params.append("priority", selectedPriority);
  }
  params.append("sortField", sortField);
  params.append("sortDir", sortDir);

  const response = await fetch(`${API_Url}?${params.toString()}`);
  if (!response.ok) {
    throw new Error("Error in fetch ToDos");
  }
  const data = await response.json();
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

export async function updateToDo(updatedData: { text: string; dueDate: string | null | undefined; doneFlag: boolean; doneDate: string | null; priority: number; }, id: number): Promise<ToDo> {
    const response = await fetch(`${API_Url}/${id}`, {
        method: "PUT",
        headers: {"Content-Type":"application/json"},
        body: JSON.stringify(updatedData)
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

