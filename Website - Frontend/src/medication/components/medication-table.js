import React from "react";
import {Button, Modal, ModalBody, ModalHeader} from 'reactstrap';
import Table from "../../commons/tables/table";
import * as API_USERS from "../api/medication-api"
import MedicationForm from "./medication-form";

const filters = [
    {
        accessor: 'name',
    }
];

class MedicationTable extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            tableData: this.props.tableData,
            selected: false,
            data: {
                id: "",
                name: "",
                side_effects: "",
                dosage: 0,
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
        this.fetchMedications();
        window.location.reload(false);
    }

    handleDelete(id) {
        this.deleteMedication(id);
    }

    deleteMedication(id) {
        return API_USERS.deleteMedication(id,(result, status, err) => {
            if (result !== null && status === 200) {
                this.setState({
                    tableData: result,
                    isLoaded: false,
                    isLoad: false

                });
                this.fetchMedications();
                window.location.reload(false);
            } else {
                this.setState(({
                    errorStatus: status,
                    error: err
                }));
            }
        });
    }

    getMedication(id) {
        return API_USERS.getMedicationById(id,(result, status, err) => {

            console.log(result);
            if (result !== null && status === 200) {
                this.setState({
                    data:{id: result.id,
                        name: result.name,
                        side_effects: result.side_effects,
                        dosage: result.dosage,},
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
        this.getMedication(id);
        this.toggleForm();
    }

    fetchMedications() {
        return API_USERS.getMedications((result, status, err) => {

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
                            Header: 'Dosage',
                            accessor: 'dosage',
                        },
                        {
                            Header: 'Side Effects',
                            accessor: 'side_effects',
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
                        {this.state.isLoad && <MedicationForm reloadHandler={this.reload} action={'update'} data={this.state.data}/>}
                    </ModalBody>
                </Modal>
            </div>
        )
    }
}

export default MedicationTable;