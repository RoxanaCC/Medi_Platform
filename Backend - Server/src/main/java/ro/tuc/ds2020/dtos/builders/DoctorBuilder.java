package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.DoctorDetailsDTO;
import ro.tuc.ds2020.entities.Doctor;
import ro.tuc.ds2020.entities.Person;

public class DoctorBuilder {

    private DoctorBuilder() {
    }

    public static DoctorDetailsDTO toDoctorDTO(Doctor doctor) {
        return new DoctorDetailsDTO(doctor.getId(), doctor.getPerson().getId(),
                doctor.getPerson().getUsername(), doctor.getPerson().getPassword(),
                doctor.getPerson().getName(), doctor.getPerson().getBirthdate(),
                doctor.getPerson().getAddress(), doctor.getPerson().getGender());
    }

    public static Doctor toEntity(DoctorDetailsDTO doctorDTO) {
        return new Doctor(new Person(doctorDTO.getUsername(),
                doctorDTO.getPassword(), doctorDTO.getName(), doctorDTO.getBirthdate(),
                doctorDTO.getAddress(), doctorDTO.getGender(), "doctor"));
    }
}
