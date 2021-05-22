import React from "react";
import Helper from "./Helper";
import {Line} from "react-chartjs-2";
import Button from "react-bootstrap/Button";

class MachinePage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            loaded: false,
            name: "",
            data: []
        };
    }

    componentDidMount() {
        Helper.fetchHelper('http://127.0.0.1:8080/observed/id/'+this.props.id, {
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify({
                credentials: this.props.credentials,
                group_id: this.props.groupId
            })
        })
            .then(json => this.setState({
                    loaded: true,
                    name: json.name,
                    data: json.data
                }),
                error => alert("Error " + error));
    }

    onCloseButtonClick = () => {
        this.props.closeMachine();
    }

    render() {
        const labels = this.state.data.map(entry => entry.timestamp);
        const cpu_t_data = this.state.data.map(entry => entry.cpu_t);
        const cpu_load_data = this.state.data.map(entry => entry.cpu_load);
        const ram_load_data = this.state.data.map(entry => entry.ram_load);

        const chart_data_cpu_t = {
            labels: labels,
            datasets: [{
                label: "CPU TEMPERATURE",
                data: cpu_t_data,
                fill: false,
                backgroundColor: 'rgb(255, 99, 132)',
                borderColor: 'rgba(255, 99, 132, 0.2)',
            }]
        };

        const chart_data_cpu_load = {
            labels: labels,
            datasets: [{
                label: "CPU LOAD",
                data: cpu_load_data,
                fill: false,
                backgroundColor: 'rgb(99, 255, 132)',
                borderColor: 'rgba(99, 255, 132, 0.2)',
            }]
        };

        const chart_data_ram_load = {
            labels: labels,
            datasets: [{
                label: "RAM LOAD",
                data: ram_load_data,
                fill: false,
                backgroundColor: 'rgb(132, 99, 255)',
                borderColor: 'rgba(132, 99, 255, 0.2)',
            }]
        };

        return(
            this.state.loaded ?
                (<div className="machine-page">
                    <h1>{this.state.name}</h1>
                    <Line data={chart_data_cpu_t} />
                    <Line data={chart_data_cpu_load} />
                    <Line data={chart_data_ram_load} />
                    <Button variant="outline-danger" onClick={this.onCloseButtonClick}>Close</Button>
                </div>) :
                (<div className="machine-page">
                    <label>Loading...</label>
                </div>)
        );
    }
}

export default MachinePage;