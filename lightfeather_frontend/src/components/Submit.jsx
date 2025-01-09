import axios from '../../node_modules/axios/index'
import { useState } from 'react'
import { useContext } from "react";
import { SupervisorContext } from '../Context'

function SubmitButton() {

    const [values, setters,] = useContext(SupervisorContext)
    const [firstName, lastName, email, phone, selectedSup] = values

    const [messageText, setMessageText] = useState('')
    const [textColor, setColor] = useState('green')
    const [buttonText, setButtonText] = useState('Submit')

    async function sendPostSubmit() {
        await axios.post('/local/api/submit', {
            "firstName": firstName,
            "lastName": lastName,
            "supervisor": selectedSup,
            "phone": phone,
            "email": email
        }, {
            headers: {
                'content-type': 'application/json'
            }
        }).then(response => {
            setColor("green")
            setMessageText(response.data.message)
        }).catch(err => {
            console.log(err)
            setColor("red")
            setMessageText(err.response.data)
        }).finally(() => {
            setters.forEach(setter => setter(""))
            setters[4]('\xa0')
            setButtonText("Submit")
        })
    }

    return (
        <div className="printBox" id="submitContainer">
            <p style={{ color: textColor }}>{messageText.replace("-", "\n")}</p>
            <div id="submitButtonContainer">
                <button id="submitButton" onClick={async () => {
                    setMessageText("")
                    setButtonText("...")
                    await sendPostSubmit()
                }}>{buttonText}</button>
            </div>
        </div>
    )
}



export default SubmitButton