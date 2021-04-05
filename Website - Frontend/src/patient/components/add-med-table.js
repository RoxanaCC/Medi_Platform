import React from "react";
import Table from "../../commons/tables/table";
import {Button} from "reactstrap";
import * as API_USERS from "../api/patient-api";

const filters = [
    {
        accessor: 'name',
    }
];

class AddMedTable extends React.Component {

    constructor(props) {
        super(props);
        this.toggleForm = this.toggleForm.bind(this);
        this.reloadHandler = this.props.reloadHandler;
        this.state = {
            tableData: this.props.tableData,
            idMP: this.props.idMP,
        };
    }

    toggleForm() {
        this.setState({collapseForm: !this.state.collapseForm});
    }

    handleAdd(id){
        this.addMedToPlan(this.state.idMP, id);
    }

    addMedToPlan(planid, medid) {
        return API_USERS.putMedToPlan(planid, medid, (result, status, error) => {
            if (result !== null && (status === 200 || status === 201)) {
                console.log("Successfully added medication to plan");
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
                        Cell: cell=>(<Button color={'secondary'} onClick={()=>{this.handleAdd(cell.value)}}>Add</Button>)
                    },
                ]}
                search={filters}
                pageSize={5}
            />
        )
    }
}

export default AddMedTable;