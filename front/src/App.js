import React from 'react';
import UserMenu from "./UserMenu";
import MachinePage from "./MachinePage";
import GroupMachines from "./GroupMachines";
import GroupList from "./GroupList";

class App extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      credentials: null,
      groupId: null,
      machineId: null
    }
  }

  onLogin = (credentials) => {
    this.setState({
      credentials: credentials
    });
  }

  onLogout = () => {
    this.setState({
      credentials: null
    });
  }

  selectGroup = (groupId) => {
    this.setState({
      groupId: groupId
    });
  }

  closeGroup = () => {
    this.setState({
      groupId: null
    });
  }

  selectMachine = (machineId) => {
    this.setState({
      machineId: machineId
    });
  }

  closeMachine = () => {
    this.setState({
      machineId: null
    });
  }

  render() {
    return (
        <div className="app">
          <UserMenu credentials={this.state.credentials} onLogin={this.onLogin} onLogout={this.onLogout}/>
          {this.state.credentials === null ?
              (<h1>Log in to start</h1>) :
              (this.state.machineId !== null ?
                  (<MachinePage id={this.state.machineId} groupId={this.state.groupId}
                                credentials={this.state.credentials}
                                closeMachine={this.closeMachine}/>) :
                  (this.state.groupId !== null ?
                      (<GroupMachines id={this.state.groupId} credentials={this.state.credentials}
                                      closeGroup={this.closeGroup} selectMachine={this.selectMachine}/>) :
                      (<GroupList credentials={this.state.credentials} selectGroup={this.selectGroup}/>)))}
        </div>
    );
  }
}

export default App;