import React from "react";
import Machine from "./Machine";

class MachineList extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            machines: props.machines
        }
    }

    onDelete = (id) => {
        this.setState((oldState, props) => {
            return {
                machines: oldState.machines.filter((elem) => elem.id !== id)
            }
        })
    }

    render() {
        return(
            <div className="machine-list">
                {this.state.machines.map((machine) =>
                    <Machine id={machine.id} onDelete={this.onDelete} machine={machine} />)}
            </div>
        );
    }
}

export default MachineList;