import React from "react";
import Table from "../../commons/tables/table";

const filters = [
    {
        accessor: 'name',
    }
];

class MedPlanTable extends React.Component {

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
                ]}
                search={filters}
                pageSize={5}
            />
        )
    }
}

export default MedPlanTable;