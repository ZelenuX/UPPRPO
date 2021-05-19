import MachineList from "./MachineList";
import Test from "./Test";

let machines = [
  {
    id: 1,
    name: "M1",
    cpuLoad: 75,
    cpuTemp: 80,
    memLoad: 34
  },
  {
    id: 2,
    name: "M2",
    cpuLoad: 50,
    cpuTemp: 55,
    memLoad: 90
  },
  {
    id: 3,
    name: "M3",
    cpuLoad: 10,
    cpuTemp: 10,
    memLoad: 10
  }
];

function App() {
  return (
    <Test />
  );
}

export default App;
