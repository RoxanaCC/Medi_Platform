package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.CaregiverDetailsDTO;
import ro.tuc.ds2020.dtos.CaregiverwithPatientsDTO;
import ro.tuc.ds2020.entities.Caregiver;
import ro.tuc.ds2020.entities.Person;


public class CaregiverBuilder {

    private CaregiverBuilder() {
    }

    public static CaregiverDetailsDTO toCaregiverDTO(Caregiver caregiver) {
        return new CaregiverDetailsDTO(caregiver.getId(), caregiver.getPerson().getId(),
                caregiver.getPerson().getUsername(), caregiver.getPerson().getPassword(),
                caregiver.getPerson().getName(), caregiver.getPerson().getBirthdate(),
                caregiver.getPerson().getAddress(), caregiver.getPerson().getGender());
    }

    public static Caregiver toEntity(CaregiverDetailsDTO caregiverDTO) {
        return new Caregiver(new Person(caregiverDTO.getUsername(), caregiverDTO.getPassword(),
                caregiverDTO.getName(), caregiverDTO.getBirthdate(), caregiverDTO.getAddress(),
                caregiverDTO.getGender(), "caregiver"));
    }

    public static CaregiverwithPatientsDTO toCaregiverwithPatientsDTO(Caregiver caregiver) {
        PatientBuilder patientBuild = new PatientBuilder();
        return new CaregiverwithPatientsDTO(caregiver.getId(), caregiver.getPerson().getId(),
                caregiver.getPerson().getUsername(), caregiver.getPerson().getPassword(),
                caregiver.getPerson().getName(), caregiver.getPerson().getBirthdate(),
                caregiver.getPerson().getAddress(), caregiver.getPerson().getGender(),
                patientBuild.toPatientDTO(caregiver.getPatients()));
    }

}
