import React from 'react';
import ProgressBar from 'react-bootstrap/ProgressBar';
import Button from 'react-bootstrap/Button';

class Machine extends React.Component {
    render() {
        let machine = this.props.machine;
        let onDelete = this.props.onDelete;
        return(
            <div>
                <h1>{machine.name}</h1>
                <ProgressBar now={machine.cpuLoad} label={"CPU load: "+machine.cpuLoad+"%"} />
                <ProgressBar now={machine.cpuTemp} label={"CPU temp: "+machine.cpuTemp+" C"} />
                <ProgressBar now={machine.memLoad} label={"Memory load: "+machine.memLoad+"%"} />
                <Button variant="outline-primary">edit</Button>
                <Button variant="outline-danger" onClick={() => onDelete(machine.id)}>delete</Button>
            </div>
        );
    }
}

export default Machine;