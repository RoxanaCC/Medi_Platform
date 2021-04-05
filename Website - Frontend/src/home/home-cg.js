import React from 'react';
import SockJsClient from 'react-stomp';
import {Client} from '@stomp/stompjs';
import {
    Button, Card,
    CardHeader, Col,
    Nav,
    Navbar,
    NavbarBrand, Row
} from 'reactstrap';
import logo from "../commons/images/icon.png";
import CarePatientsTable from "../caregiver/components/care-patients-table";
import APIResponseErrorMessage from "../commons/errorhandling/api-response-error-message";
import * as API_USERS from "../caregiver/api/caregiver-api";


class HomeCG extends React.Component {

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
            },
            isLoaded: false,
            isLoad: false,
            errorStatus: 0,
            error: null
        };
    }

    componentDidMount() {
        let id = this.props.match.params.id
        this.getCaregiver(id);
        this.fetchPatients(id);
        this.client = new Client();
        this.client.configure({
            brokerURL: 'wss://ds2020-ciorea-roxana-1-be.herokuapp.com/cg/websocket',
            onConnect: () => {
                console.log('onConnect');

                this.client.subscribe('/message/notification', message => {
                    alert(message.body);


                });
            }
        });
        this.client.activate();
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
                    <strong> Caregiver Page </strong>
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
                            <strong> Patients:  </strong>
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
                </Card>}
            </div>
        )
    };
}

export default HomeCG
