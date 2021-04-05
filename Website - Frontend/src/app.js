import React from 'react'
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import Home from './home/home';
import HomeCG from './home/home-cg';
import HomeP from "./home/home-p";
import Login from "./login/login";

import PersonContainer from './person/person-container'
import PatientContainer from './patient/patient-container'
import PatientPlansContainer from "./patient/patient-plans-container";
import DoctorContainer from './doctor/doctor-container'
import CaregiverContainer from './caregiver/caregiver-container'
import CarePatientsContainer from "./caregiver/care-patients-container";
import MedicationContainer from "./medication/medication-container";

import ErrorPage from './commons/errorhandling/error-page';
import styles from './commons/styles/project-style.css';


class App extends React.Component {


    render() {

        return (
            <div className={styles.back}>
            <Router>
                <div>
                    <Switch>

                        <Route
                            exact
                            path='/'
                            render={() => <Login/>}
                        />

                        <Route
                            exact
                            path='/dr'
                            render={() => <Home/>}
                        />

                        <Route
                            exact
                            path='/cg/:id'
                            component={HomeCG}
                        />

                        <Route
                            exact
                            path='/person'
                            render={() => <PersonContainer/>}
                        />

                        <Route
                            exact
                            path='/patient'
                            render={() => <PatientContainer/>}
                        />

                        <Route
                            exact
                            path='/patient/:id'
                            component={PatientPlansContainer}
                        />

                        <Route
                            exact
                            path='/p/:id'
                            component={HomeP}
                        />

                        <Route
                            exact
                            path='/doctor'
                            render={() => <DoctorContainer/>}
                        />

                        <Route
                            exact
                            path='/caregiver'
                            render={() => <CaregiverContainer/>}
                        />

                        <Route
                            exact
                            path='/caregiver/:id'
                            component={CarePatientsContainer}
                            //render={() => <CarePatientsContainer/>}
                        />

                        <Route
                            exact
                            path='/medication'
                            render={() => <MedicationContainer/>}
                        />

                        {/*Error*/}
                        <Route
                            exact
                            path='/error'
                            render={() => <ErrorPage/>}
                        />

                        <Route render={() =><ErrorPage/>} />
                    </Switch>
                </div>
            </Router>
            </div>
        )
    };
}

export default App
