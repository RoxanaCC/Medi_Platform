import React from "react";
import {Button, Modal, ModalBody, ModalHeader} from 'reactstrap';
import Table from "../../commons/tables/table";
import * as API_USERS from "../api/patient-api"
import PatientForm from "./patient-form";

const filters = [
    {
        accessor: 'name',
    }
];

class PatientTable extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            tableData: this.props.tableData,
            selected: false,
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
            isLoad: false
        };
        this.toggleForm = this.toggleForm.bind(this);
        this.handleUpdate = this.handleUpdate.bind(this);
        this.reload = this.reload.bind(this);
    }

    reload() {
        this.setState({
            isLoaded: false,
            isLoad: false
        });
        this.toggleForm();
        this.fetchPatients();
        window.location.reload(false);
    }

    handleDelete(id) {
        this.deletePatient(id);
    }

    deletePatient(id) {
        return API_USERS.deletePatient(id,(result, status, err) => {
            if (result !== null && status === 200) {
                this.setState({
                    tableData: result,
                    isLoaded: false,
                    isLoad: false

                });
                this.fetchPatients();
                window.location.reload(false);
            } else {
                this.setState(({
                    errorStatus: status,
                    error: err
                }));
            }
        });
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
                        gender: result.gender,
                        record: result.record,},
                    isLoad: true});
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

    handleUpdate(id) {
        this.getPatient(id);
        this.toggleForm();
    }

    fetchPatients() {
        return API_USERS.getPatients((result, status, err) => {

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

    handlePlans(id){
        window.location.pathname = "/patient/" + id
    }

    render() {
        return (
            <div>
                <Table
                    data={this.state.tableData}
                    columns = {[
                        {
                            Header: 'Name',
                            accessor: 'name',
                        },
                        {
                            Header: 'Birthdate',
                            accessor: 'birthdate',
                        },
                        {
                            Header: 'Gender',
                            accessor: 'gender',
                        },
                        {
                            Header: 'Record',
                            accessor: 'record',
                        },
                        {
                            Header: ' ',
                            accessor: 'id',
                            Cell: cell=>(<Button color={'info'} onClick={()=>{this.handlePlans(cell.value)}}>Medical Plans</Button>)
                        },
                        {
                            Header: ' ',
                            accessor: 'id',
                            Cell: cell=>(<Button color={'warning'} onClick={()=>{this.handleUpdate(cell.value)}}>Update</Button>)
                        },
                        {
                            Header: ' ',
                            accessor: 'id',
                            Cell: cell=>(<Button color={'danger'} onClick={()=>{this.handleDelete(cell.value)}}>Delete</Button>)
                        }
                    ]}
                    search={filters}
                    pageSize={5}
                />
                <Modal isOpen={this.state.selected} toggle={this.toggleForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleForm}>Update</ModalHeader>
                    <ModalBody>
                        {this.state.isLoad && <PatientForm reloadHandler={this.reload} action={'update'} data={this.state.data}/>}
                    </ModalBody>
                </Modal>
            </div>
        )
    }
}

export default PatientTable;