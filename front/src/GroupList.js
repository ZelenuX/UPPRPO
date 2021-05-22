import React from "react";
import GroupEntry from "./GroupEntry";
import GroupJoin from "./GroupJoin";
import GroupCreate from "./GroupCreate";
import Helper from "./Helper";

class GroupList extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            loaded: false,
            groups: []
        }
    }

    componentDidMount() {
        Helper.fetchHelper('http://127.0.0.1:8080/group', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify({
                credentials: this.props.credentials
            })
        })
            .then(json => this.setState({
                    loaded: true,
                    groups: json.groups
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
                        <GroupEntry key={group.id} onGroupDeleted={this.onGroupDeleted} selectGroup={this.props.selectGroup} group={group} credentials={this.props.credentials}/>)}
                    <GroupCreate onGroupCreated={this.onGroupCreated} credentials={this.props.credentials} />
                    <GroupJoin onGroupJoined={this.onGroupJoined} credentials={this.props.credentials} />
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