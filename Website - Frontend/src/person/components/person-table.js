import React from "react";
import Table from "../../commons/tables/table";

const filters = [
    {
        accessor: 'name',
    }
];

class PersonTable extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            tableData: this.props.tableData,
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
            isLoad: false
        };
    }


    render() {
        return (
            <div>
                <Table
                    data={this.state.tableData}
                    columns = {[
                        {
                            Header: 'ID',
                            accessor: 'id',
                        },
                        {
                        Header: 'Name',
                        accessor: 'name',
                        },
                        {
                        Header: 'Birthdate',
                        accessor: 'birthdate',
                        },
                        {
                        Header: 'Role',
                        accessor: 'role',
                        }
                        ]}
                    search={filters}
                    pageSize={5}
                />
            </div>
        )
    }
}

export default PersonTable;