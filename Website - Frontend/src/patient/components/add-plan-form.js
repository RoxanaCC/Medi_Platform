import React from 'react';
import validate from "../../person/components/validators/person-validators";
import Button from "react-bootstrap/Button";
import * as API_USERS from "../api/patient-api";
import APIResponseErrorMessage from "../../commons/errorhandling/api-response-error-message";
import {Col, Row} from "reactstrap";
import { FormGroup, Input, Label} from 'reactstrap';



class AddPlanForm extends React.Component {

    constructor(props) {
        super(props);
        this.toggleForm = this.toggleForm.bind(this);
        this.reloadHandler = this.props.reloadHandler;

        this.state = {

            errorStatus: 0,
            error: null,

            formIsValid: false,

            medplanid: null,
            isLoaded: false,

            formControls: {
                intake_intervals: {
                    value: '',
                    placeholder: '...',
                    valid: false,
                    touched: false,
                },
                start_date: {
                    value: '',
                    placeholder: 'DD.MM.YYYY format',
                    valid: false,
                    touched: false,
                },
                end_date: {
                    value: '',
                    placeholder: 'DD.MM.YYYY format',
                    valid: false,
                    touched: false,
                },
            }
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    toggleForm() {
        this.setState({collapseForm: !this.state.collapseForm});
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

    registerPlan(plan) {
        return API_USERS.postPlan(plan, (result, status, error) => {
            if (result !== null && (status === 200 || status === 201)) {
                console.log("Successfully inserted patient with id: " + result);
                this.setState({
                    medplanid: result,
                    isLoaded: true
                });
            } else {
                this.setState(({
                    errorStatus: status,
                    error: error
                }));
            }
        });
    }

    addPlanToPatient(patientid, planid) {
        return API_USERS.putPlanToPatient(patientid, planid,(result, status, error) => {
            if (result !== null && (status === 200 || status === 201)) {
                console.log("Successfully added plan to patient");
                this.reloadHandler();
            } else {
                this.setState(({
                    errorStatus: status,
                    error: error
                }));
            }
        });
    }

    handleSubmit (){
        let plan = {
            intake_intervals: this.state.formControls.intake_intervals.value,
            start_date: this.state.formControls.start_date.value,
            end_date: this.state.formControls.end_date.value
        };
        this.registerPlan(plan);
        setTimeout(() => {this.addPlanToPatient(this.props.idP, this.state.medplanid)}, 100);

    }

    render() {
        return (
            <div>

                <FormGroup id='intake_intervals'>
                    <Label for='intake_intervalsField'> Intake interval: </Label>
                    <Input name='intake_intervals' id='intake_intervalsField' placeholder={this.state.formControls.intake_intervals.placeholder}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.intake_intervals.value}
                           touched={this.state.formControls.intake_intervals.touched? 1 : 0}
                           valid={this.state.formControls.intake_intervals.valid}
                           required
                    />
                </FormGroup>

                <FormGroup id='start_date'>
                    <Label for='start_dateField'> Start date: </Label>
                    <Input name='start_date' id='start_dateField' placeholder={this.state.formControls.start_date.placeholder}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.start_date.value}
                           touched={this.state.formControls.start_date.touched? 1 : 0}
                           valid={this.state.formControls.start_date.valid}
                           required
                    />
                </FormGroup>

                <FormGroup id='end_date'>
                    <Label for='end_dateField'> End date: </Label>
                    <Input name='end_date' id='end_dateField' placeholder={this.state.formControls.end_date.placeholder}
                           onChange={this.handleChange}
                           defaultValue={this.state.formControls.end_date.value}
                           touched={this.state.formControls.end_date.touched? 1 : 0}
                           valid={this.state.formControls.end_date.valid}
                           required
                    />
                </FormGroup>

                <Row>
                    <Col sm={{size: '4', offset: 8}}>
                        <Button type={"submit"} disabled={!this.state.formIsValid} onClick={this.handleSubmit}> Submit </Button>
                    </Col>
                </Row>

                {
                    this.state.errorStatus > 0 &&
                    <APIResponseErrorMessage errorStatus={this.state.errorStatus} error={this.state.error}/>
                }
            </div>
        ) ;
    }
}

export default AddPlanForm;
