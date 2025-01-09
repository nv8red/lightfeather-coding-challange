import PropTypes from 'prop-types';
import { useContext } from "react";
import { SupervisorContext } from '../Context'

function PrintBox({ title, index }) {

    const [values, setters,] = useContext(SupervisorContext)
    const setter = setters[index]
    const value = values[index]

	return (
        <div className="printBox">
            <div style={{"flex-wrap": "nowrap"}}>
                <label>{title}</label>
                <br />
                <input type="text" value={value} onInput={evt => setter(evt.target.value)} />
            </div>
      </div>
  )
}

PrintBox.propTypes = {
    title: PropTypes.string,
    index: PropTypes.number
}

export default PrintBox