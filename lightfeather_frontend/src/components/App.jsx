import { useState } from 'react'
import { useEffect } from 'react'
import '../css/App.css'
import PrintBox from './PrintBox'
import { SupervisorContext } from '../Context'
import Dropdown from './Dropdown'
import SubmitButton from './Submit'
import axios from '../../node_modules/axios/index'

function App() {

    const [supervisors, setSupervisors] = useState([""])

    useEffect(() => {
        async function fetchSupervisors() {
            axios({
                method: 'get',
                url: '/local/api/supervisors',
                responseType: 'json'
            }).then(function (response) {
                setSupervisors(response.data)
            }).catch(err => alert(`Failed to load supervisors: ${err}`))
        }

        fetchSupervisors();
    }, [])

    // All textboxes are tracked in states, and I pack them up for use in the context at the bottom.
    const [firstName, setFirstName] = useState('')
    const [lastName, setLastName] = useState('')
    const [phone, setPhone] = useState('')
    const [email, setEmail] = useState('')
    const [selectedSup, setSelectedSup] = useState('\xa0')
    const setters = [setFirstName, setLastName, setPhone, setEmail, setSelectedSup]
    const values = [firstName, lastName, phone, email, selectedSup]
    
    
    return (
        <SupervisorContext.Provider value={[values,setters,supervisors]}>
            <div id="notiForm" >
                <div id="supHeader">
                    <h1>Notification Form</h1>
                </div>
                <PrintBox title="First Name" index='0' />
                <PrintBox title="Last Name" index='1' />
                <p id="middle">How would you like to be notified?</p>
                <PrintBox title="Phone Number" index='2' />
                <PrintBox title="Email" index='3' />
                <Dropdown />
                <SubmitButton />
            </div>
        </SupervisorContext.Provider>
    )
}

export default App
