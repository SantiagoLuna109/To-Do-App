import React from "react";

interface FiltersProps{
    searchTerm: string;
    onSearchChange: (value: string) => void;
    selectedPriority: string;
    onPriorityChange: (value: string) => void;
    selectedState: string;
    onStateChange: (value: string) => void;
}

const Filters: React.FC<FiltersProps> = ({
    searchTerm,
    onSearchChange,
    selectedPriority,
    onPriorityChange,
    selectedState,
    onStateChange,
}) => {
    return (
        <div className="filters">
            <input type="text" placeholder="Search name" value={searchTerm} onChange={(e)=> onSearchChange(e.target.value)} />
            <select name="" id="" value={selectedPriority} onChange={(e) => onPriorityChange(e.target.value)}>
                <option value="All">All the Priorities</option>
                <option value="1">High</option>
                <option value="2">Mediium</option>
                <option value="3">Low</option>
            </select>
            <select name="" id="" value={selectedState} onChange={(e) => onStateChange(e.target.value)}>
                <option value="All">All</option>
                <option value="Done">Done</option>
                <option value="Undone">Undone</option>
            </select>
            <button onClick={() => {}}>Search</button> 
        </div>
    );
};

export default Filters;