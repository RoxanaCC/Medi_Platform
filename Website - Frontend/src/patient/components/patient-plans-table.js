import React from "react";
import Table from "../../commons/tables/table";
import {Button, Modal, ModalBody, ModalHeader} from "reactstrap";
import AddMedTable from "./add-med-table";
import * as API_USER from "../../medication/api/medication-api";

const filters = [
    {
        accessor: 'name',
    }
];

class PatientPlansTable extends React.Component {

    constructor(props) {
        super(props);
        this.toggleForm = this.toggleForm.bind(this);
        this.reload = this.reload.bind(this);
        this.handleAdd = this.handleAdd.bind(this);
        this.state = {
            selected: false,
            collapseForm: false,
            idMP: null,
            tableData: this.props.tableData,
            tableMeds: [],
            isLoaded: false,
            errorStatus: 0,
            error: null
        };

    }

    componentDidMount() {
        this.fetchAllMeds();
    }

    fetchAllMeds() {
        return API_USER.getMedications((result, status, err) => {

            if (result !== null && status === 200) {
                this.setState({
                    tableMeds: result,
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
            isLoaded: false
        });
        this.toggleForm();
        this.fetchAllMeds();
        window.location.reload(false);
    }

    handleAdd(id){
        this.setState({idMP: id});
        this.toggleForm();
    }

    render() {
        return (
            <div>
                <Table
                    data={this.state.tableData}
                    columns = {[
                        {
                            Header: 'Intake interval',
                            accessor: 'intake_intervals',
                        },
                        {
                            Header: 'Start date',
                            accessor: 'start_date',
                        },
                        {
                            Header: 'End date',
                            accessor: 'end_date',
                        },
                        {
                            Header: 'Medications',
                            accessor: 'meds',
                        },
                        {
                            Header: ' ',
                            accessor: 'id',
                            Cell: cell=>(<Button color={'info'} onClick={()=>{this.handleAdd(cell.value)}}>Add med</Button>)
                        },
                    ]}
                    search={filters}
                    pageSize={5}
                />
                <Modal isOpen={this.state.selected} toggle={this.toggleForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleForm}> Add Patient: </ModalHeader>
                    <ModalBody>
                        <AddMedTable reloadHandler={this.reload} idMP={this.state.idMP} tableData={this.state.tableMeds}/>
                    </ModalBody>
                </Modal>
            </div>
        )
    }
}

export default PatientPlansTable;