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

import AddPatientsTable from "./components/add-patient-table";
import * as API_USERS from "./api/caregiver-api"
import * as API_USER from "../patient/api/patient-api"
import CarePatientsTable from "./components/care-patients-table";
import NavigationBar from "../navigation-bar";


class CarePatientsContainer extends React.Component {

    constructor(props) {
        super(props);
        this.toggleForm = this.toggleForm.bind(this);
        this.reload = this.reload.bind(this);
        this.state = {
            selected: false,
            collapseForm: false,
            tableData: [],
            tablePatients: [],
            data: {
                id: "",
                username: "",
                password: "",
                name: "",
                birthdate: "",
                address: "",
                gender: "",
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
        this.getCaregiver(id);
        this.fetchPatients(id);
        this.fetchAllPatients();
    }

    getCaregiver(id) {
        return API_USERS.getCaregiverById(id,(result, status, err) => {

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

    fetchPatients(id) {
        return API_USERS.getCaregiverPatients(id, (result, status, err) => {

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

    fetchAllPatients() {
        return API_USER.getPatients((result, status, err) => {

            if (result !== null && status === 200) {
                this.setState({
                    tablePatients: result,
                    isLoadP: true
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
        this.getCaregiver(id);
        this.fetchPatients(id);
        this.fetchAllPatients();
    }

    render() {
        return (
            <div>
                <NavigationBar />
                {this.state.isLoad && <CardHeader>
                    <strong> Caregiver: {this.state.data.name} </strong>
                </CardHeader>}
                <Card>
                    <br/>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            <Button color="primary" onClick={this.toggleForm}>Add Patient </Button>
                        </Col>
                    </Row>
                    <br/>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            {this.state.isLoaded && <CarePatientsTable tableData = {this.state.tableData}/>}
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
                        <AddPatientsTable reloadHandler={this.reload} idCg={this.props.match.params.id} tableData={this.state.tablePatients}/>
                    </ModalBody>
                </Modal>

            </div>
        )

    }
}


export default CarePatientsContainer;
