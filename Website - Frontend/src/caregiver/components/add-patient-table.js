import React from "react";
import Table from "../../commons/tables/table";
import {Button} from "reactstrap";
import * as API_USERS from "../api/caregiver-api";

const filters = [
    {
        accessor: 'name',
    }
];

class AddPatientsTable extends React.Component {

    constructor(props) {
        super(props);
        this.toggleForm = this.toggleForm.bind(this);
        this.reloadHandler = this.props.reloadHandler;
        this.state = {
            tableData: this.props.tableData,
            idCg: this.props.idCg,
        };
    }

    toggleForm() {
        this.setState({collapseForm: !this.state.collapseForm});
    }

    handleAdd(id){
        this.addPatientToCaregiver(this.state.idCg, id);
    }

    addPatientToCaregiver(caregiverid, patientid) {
        return API_USERS.putPatientToCaregiver(caregiverid, patientid, (result, status, error) => {
            if (result !== null && (status === 200 || status === 201)) {
                console.log("Successfully added patient to caregiver");
                this.reloadHandler();
            } else {
                this.setState(({
                    errorStatus: status,
                    error: error
                }));
            }
        });
    }

    render() {
        return (
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
                        Header: 'Record',
                        accessor: 'record',
                    },
                    {
                        Header: ' ',
                        accessor: 'id',
                        Cell: cell=>(<Button color={'secondary'} onClick={()=>{this.handleAdd(cell.value)}}>Add</Button>)
                    },
                ]}
                search={filters}
                pageSize={5}
            />
        )
    }
}

export default AddPatientsTable;