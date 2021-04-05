import React from "react";
import Table from "../../commons/tables/table";

const filters = [
    {
        accessor: 'name',
    }
];

class CarePatientsTable extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            tableData: this.props.tableData,
        };

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
                            Header: 'Address',
                            accessor: 'address',
                        },
                        {
                            Header: 'Gender',
                            accessor: 'gender',
                        },
                        {
                            Header: 'Record',
                            accessor: 'record',
                        },
                    ]}
                    search={filters}
                    pageSize={5}
                />
        )
    }
}

export default CarePatientsTable;