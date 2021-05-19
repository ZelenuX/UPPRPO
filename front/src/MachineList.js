import React from "react";
import MachineBrief from "./MachineBrief";
import MachineAdd from "./MachineAdd";

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
                    <MachineBrief key={machine.id} onDelete={this.onDelete} machine={machine} />)}
                    <MachineAdd onMachineAdded={this.onDelete} />
            </div>
        );
    }
}

export default MachineList;