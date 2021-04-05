import React from 'react';
import APIResponseErrorMessage from "../commons/errorhandling/api-response-error-message";
import {
    Button,
    Card,
    CardHeader,
    Col,
    Modal,
    ModalBody,
    ModalHeader,
    Row
} from 'reactstrap';

import AddPlanForm from "./components/add-plan-form";
import * as API_USERS from "./api/patient-api"
import PatientPlansTable from "./components/patient-plans-table";
import NavigationBar from "../navigation-bar";


class PatientPlansContainer extends React.Component {

    constructor(props) {
        super(props);
        this.toggleForm = this.toggleForm.bind(this);
        this.reload = this.reload.bind(this);
        this.state = {
            selected: false,
            collapseForm: false,
            tableData: [],
            tablePlans: [],
            data: {
                id: "",
                username: "",
                password: "",
                name: "",
                birthdate: "",
                address: "",
                gender: "",
                record: "",
            },
            isLoaded: false,
            isLoad: false,
            isLoadP: false,
            errorStatus: 0,
            error: null
        };
    }

    componentDidMount() {
        let id = this.props.match.params.id
        this.getPatient(id);
        this.fetchPlans(id);
    }

    getPatient(id) {
        return API_USERS.getPatientById(id,(result, status, err) => {

            if (result !== null && status === 200) {
                this.setState({
                    data:{id: result.id,
                        username: result.username,
                        password: result.password,
                        name: result.name,
                        birthdate: result.birthdate,
                        address: result.address,
                        gender: result.gender,},
                    isLoad: true});
            } else {
                this.setState(({
                    errorStatus: status,
                    error: err
                }));
            }
        });
    }

    fetchPlans(id) {
        return API_USERS.getPatientMedPlans(id, (result, status, err) => {

            console.log(id);
            console.log(result);
            console.log(status);
            if (result !== null && status === 200) {
                this.setState({
                    tableData: result,
                    isLoaded: true
                });
            } else {
                this.setState(({
                    errorStatus: status,
                    error: err
                }));
            }
        });
    }

    toggleForm() {
        this.setState({selected: !this.state.selected});
    }

    reload() {
        this.setState({
            isLoaded: false,
            isLoad: false,
            isLoadP: false
        });
        this.toggleForm();
        let id = this.props.match.params.id
        this.getPatient(id);
        this.fetchPlans(id);
    }

    render() {
        return (
            <div>
                <NavigationBar />
                {this.state.isLoad && <CardHeader>
                    <strong> Patient: {this.state.data.name} </strong>
                </CardHeader>}
                <Card>
                    <br/>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            <Button color="primary" onClick={this.toggleForm}>Add Plan </Button>
                        </Col>
                    </Row>
                    <br/>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            {this.state.isLoaded && <PatientPlansTable tableData = {this.state.tableData}/>}
                            {this.state.errorStatus > 0 && <APIResponseErrorMessage
                                errorStatus={this.state.errorStatus}
                                error={this.state.error}
                            />   }
                        </Col>
                    </Row>
                </Card>

                <Modal isOpen={this.state.selected} toggle={this.toggleForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleForm}> Add Patient: </ModalHeader>
                    <ModalBody>
                        <AddPlanForm reloadHandler={this.reload} idP={this.props.match.params.id}/>
                    </ModalBody>
                </Modal>

            </div>
        )

    }
}
export default PatientPlansContainer;
