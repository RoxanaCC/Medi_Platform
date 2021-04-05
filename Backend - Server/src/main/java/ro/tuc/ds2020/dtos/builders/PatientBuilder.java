package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.PatientDetailsDTO;
import ro.tuc.ds2020.dtos.PatientwithMedPlanDTO;
import ro.tuc.ds2020.entities.Patient;
import ro.tuc.ds2020.entities.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class PatientBuilder {

    PatientBuilder() {
    }

    public static PatientDetailsDTO toPatientDTO(Patient patient) {
        return new PatientDetailsDTO(patient.getId(), patient.getPerson().getId(),
                patient.getPerson().getUsername(), patient.getPerson().getPassword(),
                patient.getPerson().getName(), patient.getPerson().getBirthdate(),
                patient.getPerson().getAddress(), patient.getPerson().getGender(), patient.getRecord());
    }

    public static Patient toEntity(PatientDetailsDTO patientDTO) {
        return new Patient(patientDTO.getRecord(), new Person(patientDTO.getUsername(),
                patientDTO.getPassword(), patientDTO.getName(), patientDTO.getBirthdate(),
                patientDTO.getAddress(), patientDTO.getGender(), "patient"));
    }

    public static PatientwithMedPlanDTO toPatientwithMedPlanDTO(Patient patient) {
        MedPlanBuilder medPlanBuild = new MedPlanBuilder();
        return new PatientwithMedPlanDTO(patient.getId(), patient.getPerson().getId(),
                patient.getPerson().getUsername(), patient.getPerson().getPassword(),
                patient.getPerson().getName(), patient.getPerson().getBirthdate(),
                patient.getPerson().getAddress(), patient.getPerson().getGender(), patient.getRecord(),
                medPlanBuild.toMedPlanDTO(patient.getMedPlans()));
    }

    public static Patient toEntity(PatientwithMedPlanDTO patientMedDTO) {
        MedPlanBuilder medPlanBuild = new MedPlanBuilder();
        return new Patient(patientMedDTO.getRecord(), new Person(patientMedDTO.getUsername(),
                patientMedDTO.getPassword(), patientMedDTO.getName(), patientMedDTO.getBirthdate(),
                patientMedDTO.getAddress(), patientMedDTO.getGender(), "patient"),
                medPlanBuild.toEntity(patientMedDTO.getMeds()));
    }

    public static List<PatientDetailsDTO> toPatientDTO(List<Patient> patients) {
        List<PatientDetailsDTO> patientsDTO = new ArrayList<>();
        if(patients.isEmpty()){
            return patientsDTO;
        }
        ListIterator<Patient> iterator = patients.listIterator();
        while (iterator.hasNext()){
            Patient patient = iterator.next();
            patientsDTO.add(toPatientDTO(patient));
        }
        return patientsDTO;
    }

    public static List<Patient> toEntity(List<PatientDetailsDTO> patientsDTO) {
        List<Patient> patients = new ArrayList<>();
        ListIterator<PatientDetailsDTO> iterator = patientsDTO.listIterator();
        while (iterator.hasNext()){
            PatientDetailsDTO patientDTO = iterator.next();
            patients.add(toEntity(patientDTO));
        }
        return patients;
    }

}
