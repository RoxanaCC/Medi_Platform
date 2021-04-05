import React from "react";
import Button from "react-bootstrap/Button";
import {Col, Row} from "reactstrap";
import { Container, FormGroup, Input, Label} from 'reactstrap';
import * as API_USERS from "../person/api/person-api";
import * as API_USERSP from "../patient/api/patient-api";
import * as API_USERSC from "../caregiver/api/caregiver-api";
import {Redirect} from "react-router-dom";
import validate from "../person/components/validators/person-validators";

class Login extends React.Component{

    constructor(props) {
        super(props);
        this.handleLogin = this.handleLogin.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleRoleRedirect = this.handleRoleRedirect.bind(this);
        this.state = {
            data: {
                id: "",
                username: "",
                password: "",
                name: "",
                birthdate: "",
                address: "",
                gender: "",
                role: "",
            },
            isLoaded: false,
            id: '',
            idLoad: false,

            formControls: {
                username: {
                    value: '',
                    placeholder: 'username',
                    valid: false,
                    touched: false,
                },
                password: {
                    value: '',
                    placeholder: 'password',
                    valid: false,
                    touched: false,
                },
            },

            errorStatus: 0,
            error: null,

        }
    }

    handleChange = event => {

        const name = event.target.name;
        const value = event.target.value;

        const updatedControls = this.state.formControls;

        const updatedFormElement = updatedControls[name];

        updatedFormElement.value = value;
        updatedFormElement.touched = true;
        updatedFormElement.valid = validate(value, updatedFormElement.validationRules);
        updatedControls[name] = updatedFormElement;

        let formIsValid = true;
        for (let updatedFormElementName in updatedControls) {
            formIsValid = updatedControls[updatedFormElementName].valid && formIsValid;
        }

        this.setState({
            formControls: updatedControls,
            formIsValid: formIsValid
        });

    };


    getPerson(username, password){
        return API_USERS.getPersonByLogin(username, password,(result, status, err) => {

            if (result !== null && status === 200) {
                this.setState({
                    data:{id: result.id,
                        username: result.username,
                        password: result.password,
                        name: result.name,
                        birthdate: result.birthdate,
                        address: result.address,
                        gender: result.gender,
                        role: result.role,},
                    isLoaded: true});
            } else {
                this.setState(({
                    errorStatus: status,
                    error: err
                }));
            }
        });
    }

    getPatient(id){
        return API_USERSP.getPatientByFkey(id,(result, status, err) => {

            if (result !== null && status === 200) {
                this.setState({
                    id: result,
                    isLoad: true});
            } else {
                this.setState(({
                    errorStatus: status,
                    error: err
                }));
            }
        });
    }

    getCaregiver(id){
        return API_USERSC.getCaregiverByFkey(id,(result, status, err) => {

            if (result !== null && status === 200) {
                this.setState({
                    id: result,
                    isLoad: true});
                console.log(result);
            } else {
                this.setState(({
                    errorStatus: status,
                    error: err
                }));
            }
        });
    }

    handleLogin(){
        let username = this.state.formControls.username.value;
        let password = this.state.formControls.password.value;
        this.getPerson(username, password);
        setTimeout(() => {this.handleRoleRedirect()}, 100);

    }

    handleRoleRedirect = () =>{
        {
            if(this.state.data.role ==="doctor")
                window.location.pathname = '/dr'
            if(this.state.data.role ==="patient") {
                console.log("patient");
                this.getPatient(this.state.data.id);
                setTimeout(() => {return (window.location.pathname = '/p/' + this.state.id.id)}, 100);

            }
            if(this.state.data.role ==="caregiver") {
                console.log("caregiver");
                this.getCaregiver(this.state.data.id);
                setTimeout(() => {return (window.location.pathname = '/cg/' + this.state.id.id)}, 100);
            }
            //this.setState({isLoaded: false});

        }

    }


    render() {
        return (
            <div style={{backgroundColor:'white'}}>
                <br/>
                <br/>
                <Container  className={"Login"} md={{ size: 6, offset: 3 }}>
                    <br/>
                    <br/>
                    <br/>
                    <br/>
                    <h1 className={"text-center"}>Log In Here</h1>
                    <br/>
                    <Col md={{ size: 6, offset: 3 }}>
                        <FormGroup id='username'>
                            <Label for='usernameField'> Username: </Label>
                            <Input name='username' id='usernameField' placeholder={this.state.formControls.username.placeholder}
                                   onChange={this.handleChange}
                                   defaultValue={this.state.formControls.username.value}
                                   touched={this.state.formControls.username.touched? 1 : 0}
                                   valid={this.state.formControls.username.valid}
                                   required
                            />
                        </FormGroup>
                    </Col>
                    <br/>
                    <Col  md={{ size: 6, offset: 3 }}>
                        <FormGroup id='password'>
                            <Label for='passwordField'> Password: </Label>
                            <Input type='password' name='password' id='passwordField' placeholder={this.state.formControls.password.placeholder}
                                   onChange={this.handleChange}
                                   defaultValue={this.state.formControls.password.value}
                                   touched={this.state.formControls.password.touched? 1 : 0}
                                   valid={this.state.formControls.password.valid}
                                   required
                            />
                        </FormGroup>
                    </Col>
                    <br/>
                    <br/>
                    <Col className={"text-center"}>
                        <Button color={'info'} onClick={this.handleLogin}>Login</Button>
                        {this.state.errorStatus>250 &&
                        <div style={{color: "red", fontSize: "26px"}}  className={"error-message"}> * Wrong username or password!</div>}

                    </Col>
                    <br/>
                    <br/>
                    <br/>
                    <br/>
                    <br/>
                </Container>
            </div>
        )

    }

}

export default Login
