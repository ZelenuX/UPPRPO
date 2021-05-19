import React from "react";
import { Line } from "react-chartjs-2";
import {Chart} from "chart.js";

const data = {
    labels: ['1', '2', '3', '4', '5', '6', '7', '8'],
    datasets: [{
        label: "sample data",
        data: [1, 2, 3, 4, 1, 3, 0, 2]
    }]
}

class Test extends React.Component {
    constructor(props) {
        super(props);

        this.data = {
            prevTickNumber: 1,
            labels: ['0', '1'],
            data: [0, 1]
        }

        this.state = {
            prevTickNumber: 0,
            labels: ['0'],
            data: [0]
        };

        this.chartRef = React.createRef();
    }

    componentDidMount() {
        // const timerId = setInterval(() => {
        //     // this.setState((prevState, props) => {
        //     //     const tickNumber = prevState.prevTickNumber + 1;
        //     //     return {
        //     //         prevTickNumber: tickNumber,
        //     //         labels: prevState.labels.concat([tickNumber.toString()]),
        //     //         data: prevState.data.concat([tickNumber % 11])
        //     //     }
        //     // });
        //     const tickNumber = this.data.prevTickNumber + 1;
        //     this.data.labels.push(tickNumber.toString());
        //     this.data.data.push(tickNumber % 11);
        //     this.data.prevTickNumber += 1;
        // }, 1000);
        // this.setState({
        //     timerId: timerId
        // });

        const labels = ['a', 'b', 'c', 'd', 'f', 'e', 'l'];
        const data = {
            labels: labels,
            datasets: [{
                label: 'My First Dataset',
                data: [65, 59, 80, 81, 56, 55, 40],
                fill: false,
                borderColor: 'rgb(75, 192, 192)',
                tension: 0.1
            }]
        };

        const config = {
            type: 'line',
            data: data,
        };

        const myLineChart = new Chart(document.getElementById('myChart'), config);
        // myLineChart.update();
        console.log("aadas");
    }

    componentWillUnmount() {
        //clearInterval(this.state.timerId);
    }

    render() {
        return(
            <canvas id="myChart" />
        );
    }
}

export default Test;