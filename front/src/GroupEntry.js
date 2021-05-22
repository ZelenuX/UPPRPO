import React from "react";
import Helper from "./Helper";

class GroupEntry extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            sentDelete: false
        };
    }

    onDeleteButtonClick = () => {
        this.setState({
            sentDelete: true
        });
    }

    onGroupReferenceClick = (event) => {
        event.preventDefault();
        this.props.selectGroup(this.props.group.id);
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if(this.state.sentDelete && !prevState.sentDelete) {
            Helper.fetchHelper('http://127.0.0.1:8080/group/exit', {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json;charset=utf-8'
                },
                body: JSON.stringify({
                    credentials: this.props.credentials,
                    id: this.props.group.id,
                    name: this.props.group.name
                })
            })
                .then(() => this.props.onGroupDeleted(this.props.group.id),
                    error => {
                    alert("Error: "+error);
                    this.setState({
                        sentDelete: false
                    });
                });
        }
    }

    render() {
        return this.state.sentDelete ?
            (<div className="group-entry">
                <label>Loading...</label>
            </div>) :
            (<div className="group-entry">
                <a href="" onClick={this.onGroupReferenceClick}>
                    {this.props.group.name}
                </a>
                <button onClick={this.onDeleteButtonClick}>Delete</button>
            </div>);
    }
}

export default GroupEntry;