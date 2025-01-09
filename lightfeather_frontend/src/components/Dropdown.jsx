import { useState } from 'react'
import { useContext } from "react";
import { SupervisorContext } from '../Context'
import PropTypes from 'prop-types';
import { useRef, useEffect } from "react";
import '../css/App.css'


function Dropdown() {

    const [values, ,] = useContext(SupervisorContext)
    const [dropdownVisible, setDropVisible] = useState(false)

    const selectedSup = values[4]

    return (
        <div id="dropDownContainer">
        <label htmlFor="dropButton">Supervisor<br /></label>
            <button id="dropButton" onClick={() => setDropVisible(!dropdownVisible)}>
                {selectedSup}
            </button>
            <div id="scrollTable">
                {dropdownVisible ? (<DropdownMenu setDropVisible={setDropVisible} />) : <></>}
            </div>
        </div>
    );
};

function DropdownMenu({ setDropVisible }) {

    const [values, setters, supervisors] = useContext(SupervisorContext)
    const [searchStr, setSearchStr] = useState("")
    const setSelectedSup = setters[4]
    const selectedSup = values[4]

    // Clicking outside the dropdown menu closes it.
    const dropdownRef = useRef(null);
    useEffect(() => {
        function handleClickOutside(event) {
            if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
                setDropVisible(false)
            }
        }

        document.addEventListener("mousedown", handleClickOutside);
        return () => {
            document.removeEventListener("mousedown", handleClickOutside);
        };
    }, [dropdownRef, setDropVisible, setSelectedSup]);

    return (
        <table ref={dropdownRef}>
            <tr key="dropDownTextRow">
                <th key="dropDownTextCell">
                    <input id="dropTextBox" type="text" autoFocus
                        onInput={evt => setSearchStr(evt.target.value)}
                        // Pressing enter or tab also closes the dropdown menu.
                        onKeyDown={evt => {
                            if ("Enter" === evt.key || "Tab" === evt.key) {
                                setDropVisible(false)
                            }
                        }}
                    />
                </th>
            </tr>
            {filterSupervisors(searchStr, supervisors, setSelectedSup).map(supervisor =>
                <tr key={supervisor + "Row"}>
                    <th key={supervisor + "Cell"} className={isSelected(selectedSup, supervisor)} onClick={() => setSelectedSup(supervisor)}>
                        {supervisor}
                    </th>
                </tr>
            )}
        </table>
    );
}

// Used for changing the color of the selected supervisor.
function isSelected(selectedSup, supervisor) {
    if (selectedSup == supervisor) {
        return "selectedSup";
    }
    return "notSelected";

}

// Search function looks at firstname, lastname, and jurisdiction.
function filterSupervisors(str, sups, setSelectedSup) {
    if (str == "") {
        return sups
    }
    var filteredSups = sups.filter(sup =>
        sup.split(/[ ,-]+/).map(substr =>
            substr.toLowerCase().startsWith(str))
        .includes(true))
    if (filteredSups.length == 1) {
        setSelectedSup(filteredSups[0])
    }
    return filteredSups;
}

DropdownMenu.propTypes = {
    setDropVisible: PropTypes.func
}

export default Dropdown