import React, { useState } from "react";
import '../styles/Filters.css';
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
    const [tempSearch, setTempSearch] = useState(searchTerm);
    const [tempPriority, setTempPriority] = useState(selectedPriority);
    const [tempState, setTempSate] = useState(selectedState);
    const handleSearchClick = () => {
        onSearchChange (tempSearch);
        onPriorityChange(tempPriority);
        onStateChange(tempState);
    };
    const handleResetFilters = () => {
        setTempPriority("All");
        setTempSate("All");
        setTempSearch("");
        onPriorityChange("All");
        onSearchChange("");
        onStateChange("All");
    };
    return (
        <div className="filters">
            <div className="filters-row">
                <h4>Name: </h4>
                <input type="text" placeholder="Search name" value={tempSearch} onChange={(e)=> setTempSearch(e.target.value)} />
            </div>
            <div className="filters-row">
                <h4>Priority: </h4>
                <select name="" id="" value={tempPriority} onChange={(e) => setTempPriority(e.target.value)}>
                    <option value="All">All the Priorities</option>
                    <option value="1">High</option>
                    <option value="2">Medium</option>
                    <option value="3">Low</option>
                </select>
            </div>
            <div className="filters-row">
                <h4>State: </h4>
                <select name="" id="" value={tempState} onChange={(e) => setTempSate(e.target.value)}>
                    <option value="All">All</option>
                    <option value="Done">Done</option>
                    <option value="Undone">Undone</option>
                </select>
            </div>
            <div className="button-container">
                <button onClick={handleSearchClick}>Search</button> 
                <button onClick={handleResetFilters}>Reset Filters</button>
            </div>
        </div>
    );
};

export default Filters;