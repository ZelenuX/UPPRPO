import React from "react";
import MachineBrief from "./MachineBrief";
import MachineAdd from "./MachineAdd";
import Helper from "./Helper";

class GroupMachines extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            loaded: false,
            machines: []
        }
    }

    componentDidMount() {
        Helper.fetchHelper('http://127.0.0.1:8080/group/id/'+this.props.group.id, {
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify({
                credentials: this.props.credentials,
                name: this.props.group.name
            })
        })
            .then(json => this.setState({
                    loaded: true,
                    machines: json.observed
                }),
                error => alert("Error " + error));
    }

    onDelete = (id) => {
        this.setState((oldState, props) => {
            return {
                machines: oldState.machines.filter((elem) => elem.id !== id)
            };
        })
    }

    onAdd = (machine) => {
        this.setState((oldState, props) => {
            return {
                machines: oldState.machines.concat(machine)
            };
        })
    }

    onCloseButtonClick = () => {
        this.props.closeGroup();
    }

    render() {
        return(
            <div className="machine-list">
                {this.state.machines.map((machine) =>
                    <MachineBrief key={machine.id} groupId={this.props.group.id} onDelete={this.onDelete} selectMachine={this.props.selectMachine} machine={machine} />)}
                    <MachineAdd onMachineAdded={this.onAdd} credentials={this.props.credentials} />
                    <button onClick={this.onCloseButtonClick}>Close</button>
            </div>
        );
    }
}

export default GroupMachines;