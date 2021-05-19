import React from "react";

class GroupEntry extends React.Component {
    constructor(props) {
        super(props);

        this.setState({
            sentDelete: false
        });
    }

    onDeleteButtonClick = () => {
        this.setState({
            sentDelete: true
        });
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if(this.state.sentDelete && !prevState.sentDelete) {
            fetch('http://127.0.0.1:8080/api/group/' + this.props.group.id, {method: 'DELETE'})
                .then(response => new Promise(
                    (resolve, reject) => response.ok ?
                        resolve(this.props.onGroupDeleted(this.props.group.id)) :
                        reject(response.error())))
                .catch(reason => {
                    alert(reason);
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
                <label>
                    {this.props.group.name}
                </label>
                <button onClick={this.onDeleteButtonClick}>Delete</button>
            </div>);
    }
}

export default GroupEntry;