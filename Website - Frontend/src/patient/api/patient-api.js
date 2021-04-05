import {HOST} from '../../commons/hosts';
import RestApiClient from "../../commons/api/rest-client";


const endpoint = {
    patient: '/patient'
};

function getPatients(callback) {
    let request = new Request(HOST.backend_api + endpoint.patient, {
        method: 'GET',
    });
    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function getPatientById(id, callback){
    let request = new Request(HOST.backend_api + endpoint.patient + "/" + id, {
        method: 'GET'
    });

    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function getPatientMedPlans(id, callback){
    let request = new Request(HOST.backend_api + endpoint.patient + "/" + id + "/medplan", {
        method: 'GET'
    });

    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function getPatientByFkey(id, callback){
    let request = new Request(HOST.backend_api + endpoint.patient + "/" + id + "/fkey", {
        method: 'GET'
    });

    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function postPatient(user, callback){
    let request = new Request(HOST.backend_api + endpoint.patient , {
        method: 'POST',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(user)
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}

function postPlan(user, callback){
    let request = new Request(HOST.backend_api + '/medplan', {
        method: 'POST',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(user)
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}

function deletePatient(id, callback) {
    let request = new Request(HOST.backend_api + endpoint.patient + '/' + id, {
        method: 'DELETE',
    });
    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function putPatient(id, user, callback){
    let request = new Request(HOST.backend_api + endpoint.patient + '/' + id, {
        method: 'PUT',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(user)
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}

function putPlanToPatient(patientid, planid, callback){
    let request = new Request(HOST.backend_api + endpoint.patient + '/' + patientid + '/medplan/' + planid, {
        method: 'PUT',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
    });
    console.log("URL: " + request.url);
    RestApiClient.performRequest(request, callback);
}

function putMedToPlan(planid, medid, callback){
    let request = new Request(HOST.backend_api + '/medplan/' + planid + '/medication/' + medid, {
        method: 'PUT',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
    });
    console.log("URL: " + request.url);
    RestApiClient.performRequest(request, callback);
}

export {
    getPatients,
    getPatientById,
    postPatient,
    deletePatient,
    putPatient,
    getPatientMedPlans,
    putPlanToPatient,
    putMedToPlan,
    postPlan,
    getPatientByFkey
};
