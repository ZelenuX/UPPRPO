import React from 'react';
import ProgressBar from 'react-bootstrap/ProgressBar';
import Button from 'react-bootstrap/Button';
import Helper from "./Helper";

class MachineBrief extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            sentDelete: false
        };
    }

    onMachineReferenceClick = (event) => {
        event.preventDefault();
        this.props.selectMachine(this.props.machine.id);
    }

    onDeleteButtonClick = () => {
        this.setState({
            sentDelete: true
        });

        Helper.fetchText('http://127.0.0.1:8080/group/id/'+this.props.groupId+'/remove', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify({
                credentials: this.props.credentials,
                observed_id: this.props.machine.id
            })
        })
            .then(text => this.props.onDelete(this.props.machine.id),
                error => {
                    alert("Error: "+error);
                    this.setState({
                        sentDelete: false
                    });
                });
    }

    render() {
        let machine = this.props.machine;
        return (
            this.state.sentDelete ?
                (<div className="machine-brief">
                    <label>Loading...</label>
                </div>) :
                (<div className="machine-brief">
                    <a href="" onClick={this.onMachineReferenceClick}>{machine.name}</a>
                    <ProgressBar now={machine.cpu_load} label={"CPU load: " + machine.cpu_load + "%"}/>
                    <ProgressBar now={machine.cpu_t} label={"CPU temp: " + machine.cpu_t + " C"}/>
                    <ProgressBar now={machine.ram_load} label={"Memory load: " + machine.ram_load + "%"}/>
                    <Button variant="outline-danger" onClick={this.onDeleteButtonClick}>Delete</Button>
                </div>)
        );
    }
}

export default MachineBrief;