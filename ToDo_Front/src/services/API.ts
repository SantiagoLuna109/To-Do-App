import { ToDo }     from "../types/ToDo";
import { Metrics }  from "../types/Metrics";

const API_URL = "http://localhost:9090/todos";

export async function fetchToDos(
  page: number,
  size: number,
  searchTerm: string,
  selectedPriority: string,
  selectedState: string,
  sortField: string,
  sortDir: "asc" | "desc"
) {
  const params = new URLSearchParams();
  params.append("page", String(page));
  params.append("size", String(size));

  if (searchTerm)                 params.append("text", searchTerm);
  if (selectedPriority !== "All") params.append("priority", selectedPriority);
  if (selectedState === "Done")   params.append("done",  "true");
  if (selectedState === "Undone") params.append("done",  "false");

  params.append("sort", `${sortField},${sortDir}`);

  const res = await fetch(`${API_URL}?${params.toString()}`);
  if (!res.ok) throw new Error(res.statusText);
  return res.json();                         
}

export async function fetchMetrics(
  searchTerm: string,
  selectedPriority: string,
  selectedState: string
): Promise<Metrics> {
  const params = new URLSearchParams();
  if (searchTerm)                 params.append("text", searchTerm);
  if (selectedPriority !== "All") params.append("priority", selectedPriority);
  if (selectedState === "Done")   params.append("done",  "true");
  if (selectedState === "Undone") params.append("done",  "false");

  const res = await fetch(`${API_URL}/metrics?${params.toString()}`);
  if (!res.ok) throw new Error(res.statusText);
  return res.json();
}

export async function createToDo(
  data: Omit<ToDo, "id" | "creationDate">
): Promise<ToDo> {
  const res = await fetch(API_URL, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });
  if (!res.ok) throw new Error(res.statusText);
  return res.json();
}

export async function updateToDo(
  data: {
    text: string;
    dueDate?: string | null;
    doneFlag: boolean;
    doneDate?: string | null;
    priority: number;
  },
  id: number
): Promise<ToDo> {
  const res = await fetch(`${API_URL}/${id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  });
  if (!res.ok) throw new Error(res.statusText);
  return res.json();
}

export async function deleteToDo(id: number): Promise<void> {
  const res = await fetch(`${API_URL}/${id}`, { method: "DELETE" });
  if (!res.ok) throw new Error(res.statusText);
}


