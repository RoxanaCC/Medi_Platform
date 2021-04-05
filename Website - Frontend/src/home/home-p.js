import React from 'react';
import {
    Card,
    CardHeader, Col,
    Nav,
    Navbar,
    NavbarBrand, Row
} from 'reactstrap';
import logo from "../commons/images/icon.png";
import * as API_USERS from "../patient/api/patient-api";
import APIResponseErrorMessage from "../commons/errorhandling/api-response-error-message";
import MedPlanTable from "./components/med-plan-table";

class HomeP extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            tableData: [],
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
            errorStatus: 0,
            error: null
        };
    }

    componentDidMount() {
        let id = this.props.match.params.id
        this.getPatient(id);
        this.fetchMedPlans(id);
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

    fetchMedPlans(id) {
        return API_USERS.getPatientMedPlans(id, (result, status, err) => {

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
                <Navbar color="dark" light expand="md">
                    <NavbarBrand href="/">
                        <img src={logo} width={"50"}
                             height={"35"} />
                    </NavbarBrand>
                    <Nav className="mr-auto" navbar>
                    </Nav>
                </Navbar>
                <CardHeader>
                    <strong> Patient Page </strong>
                </CardHeader>
                {this.state.isLoaded && <Card>
                    <br/>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            <strong> Name: {this.state.data.name} </strong>
                        </Col>
                    </Row>
                    <br/>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            <strong> Birthdate: {this.state.data.birthdate} </strong>
                        </Col>
                    </Row>
                    <br/>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            <strong> Address: {this.state.data.address} </strong>
                        </Col>
                    </Row>
                    <br/>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            <strong> Medical record: {this.state.data.record} </strong>
                        </Col>
                    </Row>
                    <br/>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            <strong> Medical Plans:  </strong>
                        </Col>
                    </Row>
                    <br/>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            {this.state.isLoaded && <MedPlanTable tableData = {this.state.tableData}/>}
                            {this.state.errorStatus > 0 && <APIResponseErrorMessage
                                errorStatus={this.state.errorStatus}
                                error={this.state.error}
                            />   }
                        </Col>
                    </Row>
                </Card>}
            </div>
        )
    };
}

export default HomeP