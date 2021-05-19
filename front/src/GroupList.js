import React from "react";
import GroupEntry from "./GroupEntry";
import GroupJoin from "./GroupJoin";
import GroupCreate from "./GroupCreate";

class GroupList extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            loaded: false,
            groups: []
        }
    }

    componentDidMount() {
        fetch('http://127.0.0.1:8080/api/group/')
            .then(response => response.ok ?
                response.json() :
                new Promise((resolve, reject) => reject(response.error()))
            )
            .then(json => this.setState({
                    loaded: true,
                    groups: json
                }),
                error => alert("Error " + error));
    }

    onGroupDeleted = (id) => {
        this.setState((oldState, props) => {
            return {
                groups: oldState.groups.filter((elem) => elem.id !== id)
            }
        })
    }

    onGroupCreated = (createdGroup) => {
        this.setState((prevState, props) => {
            return {
                groups: prevState.groups.concat([createdGroup])
            }
        });
    }

    onGroupJoined = (joinedGroup) => {
        this.setState((prevState, props) => {
            return {
                groups: prevState.groups.concat([joinedGroup])
            }
        });
    }

    render() {
        if(this.state.loaded) {
            return (
                <div className="group-list">
                    {this.state.groups.map((group) =>
                        <GroupEntry key={group.id} onGroupDeleted={this.onGroupDeleted} group={group}/>)}
                    <GroupCreate onGroupCreated={this.onGroupCreated}/>
                    <GroupJoin onGroupJoined={this.onGroupJoined} />
                </div>
            );
        } else {
            return(
                <div className="group-list">
                    <label>Loading...</label>
                </div>
            );
        }
    }
}

export default GroupList;