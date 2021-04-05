package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.DoctorDetailsDTO;
import ro.tuc.ds2020.dtos.builders.DoctorBuilder;
import ro.tuc.ds2020.entities.Doctor;
import ro.tuc.ds2020.entities.Person;
import ro.tuc.ds2020.repositories.DoctorRepository;
import ro.tuc.ds2020.repositories.PersonRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorService.class);
    private final ro.tuc.ds2020.repositories.DoctorRepository DoctorRepository;
    private final PersonRepository PersonRepository;

    @Autowired
    public DoctorService(DoctorRepository DoctorRepository, PersonRepository PersonRepository) {
        this.DoctorRepository = DoctorRepository;
        this.PersonRepository = PersonRepository;
    }

    public List<DoctorDetailsDTO> findDoctors() {
        List<Doctor> doctorList = DoctorRepository.findAll();
        return doctorList.stream()
                .map(DoctorBuilder::toDoctorDTO)
                .collect(Collectors.toList());
    }

    public DoctorDetailsDTO findDoctorById(UUID id) {
        Optional<Doctor> doctorOptional = DoctorRepository.findById(id);
        if (!doctorOptional.isPresent()) {
            LOGGER.error("Doctor with id {} was not found in db", id);
            throw new ResourceNotFoundException(Doctor.class.getSimpleName() + " with id: " + id);
        }
        return DoctorBuilder.toDoctorDTO(doctorOptional.get());
    }

    public UUID insert(DoctorDetailsDTO doctorDTO) {
        Doctor doctor = DoctorBuilder.toEntity(doctorDTO);
        doctor = DoctorRepository.save(doctor);
        LOGGER.debug("Doctor with id {} was inserted in db", doctor.getId());
        return doctor.getId();
    }

    public UUID update(DoctorDetailsDTO doctorDTO, UUID id) {
        Optional<Doctor> DoctorOptional = DoctorRepository.findById(id);
        if (!DoctorOptional.isPresent()) {
            LOGGER.error("Doctor with id {} was not found in db");
            throw new ResourceNotFoundException(Doctor.class.getSimpleName() + " with id ");
        }
        delete(id);
        UUID rez = insert(doctorDTO);
        return rez;
    }

    public void delete(UUID id){
        Optional<Doctor> doctorOptional = DoctorRepository.findById(id);
        if (!doctorOptional.isPresent()) {
            LOGGER.error("Doctor with id {} was not found in db");
            throw new ResourceNotFoundException(Doctor.class.getSimpleName() + " with id ");
        }
        this.DoctorRepository.deleteById(id);
        LOGGER.debug("Doctor with id {} was deleted from the db", id);
    }
}
