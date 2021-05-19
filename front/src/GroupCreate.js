import React from "react";

class GroupCreate extends React.Component {
    constructor(props) {
        super(props);

        this.setState({
            activated: false,
            postSent: false,
            groupName: "",
            groupPassword: ""
        });
    }

    onCreateButtonClick = () => {
        this.setState({
            activated: true
        })
    }

    onGroupNameFieldEdit = (event) => {
        this.setState({
            groupName: event.target.value
        });
    }

    onGroupPasswordFieldEdit = (event) => {
        this.setState({
            groupPassword: event.target.value
        });
    }

    onSaveButtonClick = () => {
        this.setState({
            postSent: true
        });
    }

    onCancelButtonClick = () => {
        this.setState({
            activated: false,
            postSent: false,
            groupName: "",
            groupPassword: ""
        });
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if(this.state.postSent && !prevState.postSent) {
            fetch('http://127.0.0.1:8080/api/group/', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json;charset=utf-8'
                },
                body: JSON.stringify({
                    name: prevState.groupName,
                    password: prevState.groupPassword
                })
            })
                .then(response => response.ok ?
                    response.json() :
                    new Promise((resolve, reject) => reject(response.error()))
                )
                .then(json => {
                        this.props.onGroupCreated(json);
                        this.setState({
                            activated: false,
                            postSent: false,
                            groupName: "",
                            groupPassword: ""
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
                (<div className="group-create">
                    <label>Sending...</label>
                </div>) :
                (<div className="group-create">
                    <label>
                        Name:
                        <input type="text" value={this.state.groupName} onChange={this.onGroupNameFieldEdit} />
                    </label>
                    <label>
                        Password:
                        <input type="text" value={this.state.groupPassword} onChange={this.onGroupPasswordFieldEdit} />
                    </label>
                    <button onClick={this.onSaveButtonClick}>Save</button>
                    <button onClick={this.onCancelButtonClick}>Cancel</button>
                </div>);
        } else {
            return(
                <div className="group-create">
                    <button onClick={this.onCreateButtonClick}>Create</button>
                </div>
            );
        }
    }
}

export default GroupCreate;