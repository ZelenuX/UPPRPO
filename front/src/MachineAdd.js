import React from "react";

class MachineAdd extends React.Component {
    constructor(props) {
        super(props);

        this.setState({
            activated: false,
            postSent: false,
            machineName: "",
            machinePassword: ""
        });
    }

    onOuterAddButtonClick = () => {
        this.setState({
            activated: true
        })
    }

    onMachineNameFieldEdit = (event) => {
        this.setState({
            machineName: event.target.value
        });
    }

    onMachinePasswordFieldEdit = (event) => {
        this.setState({
            machinePassword: event.target.value
        });
    }

    onInnerAddButtonClick = () => {
        this.setState({
            postSent: true
        });
    }

    onCancelButtonClick = () => {
        this.setState({
            activated: false,
            postSent: false,
            machineName: "",
            machinePassword: ""
        });
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if(this.state.postSent && !prevState.postSent) {
            fetch('http://127.0.0.1:8080/api/addMachine/', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json;charset=utf-8'
                },
                body: JSON.stringify({
                    name: prevState.machineName,
                    password: prevState.machinePassword
                })
            })
                .then(response => response.ok ?
                    response.json() :
                    new Promise((resolve, reject) => reject(response.error()))
                )
                .then(json => {
                        this.props.onMachineAdded(json);
                        this.setState({
                            activated: false,
                            postSent: false,
                            machineName: "",
                            machinePassword: ""
                        });
                    },
                    error => {
                        this.setState({
                            postSent: false
                        });
                        alert("Error: " + error);
                    });
        }
    }

    render() {
        if(this.state.activated) {
            return this.state.postSent ?
                (<div className="machine-add">
                    <label>Adding...</label>
                </div>) :
                (<div className="machine-add">
                    <label>
                        Name:
                        <input type="text" value={this.state.machineName} onChange={this.onMachineNameFieldEdit} />
                    </label>
                    <label>
                        Password:
                        <input type="text" value={this.state.machinePassword} onChange={this.onMachinePasswordFieldEdit} />
                    </label>
                    <button onClick={this.onInnerAddButtonClick}>Add</button>
                    <button onClick={this.onCancelButtonClick}>Cancel</button>
                </div>);
        } else {
            return(
                <div className="machine-add">
                    <button onClick={this.onOuterAddButtonClick}>Add</button>
                </div>
            );
        }
    }
}

export default MachineAdd;