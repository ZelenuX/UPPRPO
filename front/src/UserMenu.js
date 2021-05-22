import React from "react";
import Helper from "./Helper";

class UserMenu extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            signInActivated: false,
            signUpActivated: false,
            usernameFieldValue: "",
            passwordFieldValue: "",
            submitSent: false
        };
    }

    onUsernameFieldChange = (event) => {
        this.setState({
            usernameFieldValue: event.target.value
        });
    }

    onPasswordFieldChange = (event) => {
        this.setState({
            passwordFieldValue: event.target.value
        });
    }

    onSignInButtonClick = () => {
        this.setState({
            signInActivated: true
        });
    }

    onSignUpButtonClick = () => {
        this.setState({
            signUpActivated: true
        });
    }

    onCancelButtonClick = () => {
        this.setState({
            signInActivated: false,
            signUpActivated: false,
            usernameFieldValue: "",
            passwordFieldValue: ""
        });
    }

    onSignInSubmitButtonClick = () => {
        this.setState({
            submitSent: true
        });

        Helper.fetchText("http://127.0.0.1:8080/user/login", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify({
                username: this.state.username,
                password: this.state.password
            })
        })
            .then(text => {
                this.props.onLogin({
                    username: this.state.usernameFieldValue,
                    password: this.state.passwordFieldValue
                });
                this.setState({
                    signInActivated: false,
                    signUpActivated: false,
                    usernameFieldValue: "",
                    passwordFieldValue: "",
                    submitSent: false
                });
            },
                error => {
                    alert("Error: "+error);
                    this.setState({
                        submitSent: false
                    });
                });
    }

    onSignUpSubmitButtonClick = () => {
        this.setState({
            submitSent: true
        });

        Helper.fetchText("http://127.0.0.1:8080/user/register", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify({
                username: this.state.usernameFieldValue,
                password: this.state.passwordFieldValue
            })
        })
            .then(text => {
                    this.props.onLogin({
                        username: this.state.usernameFieldValue,
                        password: this.state.passwordFieldValue
                    });
                    this.setState({
                        signInActivated: false,
                        signUpActivated: false,
                        usernameFieldValue: "",
                        passwordFieldValue: "",
                        submitSent: false
                    });
                },
                error => {
                    alert("Error: "+error);
                    this.setState({
                        submitSent: false
                    });
                });
    }

    render() {
        if(this.state.submitSent) {
            return(
                <div className="user-menu">
                    <label>Loading..</label>
                </div>
            );
        } else if(this.props.credentials !== null) {
            return(
                <div className="user-menu">
                    <label>{this.props.credentials.username} {this.props.credentials.password}</label>
                    <button onClick={this.props.onLogout}>Logout</button>
                </div>
            );
        } else if(this.state.signInActivated || this.state.signUpActivated) {
            return(
                <div className="user-menu">
                    <label>Username</label>
                    <input type="text" onChange={this.onUsernameFieldChange} />
                    <label>Password</label>
                    <input type="text" onChange={this.onPasswordFieldChange} />
                    <button onClick={this.state.signInActivated ? this.onSignInSubmitButtonClick : this.onSignUpSubmitButtonClick}>
                        {this.state.signInActivated ? "Sign In" : "Sign Up"}
                    </button>
                    <button onClick={this.onCancelButtonClick}>Cancel</button>
                </div>
            );
        } else {
            return(
                <div className="user-menu">
                    <button onClick={this.onSignInButtonClick}>Sign In</button>
                    <button onClick={this.onSignUpButtonClick}>Sign Up</button>
                </div>
            );
        }
    }
}

export default UserMenu;