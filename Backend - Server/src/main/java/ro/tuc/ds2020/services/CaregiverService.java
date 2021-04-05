package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.CaregiverDetailsDTO;
import ro.tuc.ds2020.dtos.PatientDetailsDTO;
import ro.tuc.ds2020.dtos.builders.CaregiverBuilder;
import ro.tuc.ds2020.dtos.builders.PatientBuilder;
import ro.tuc.ds2020.entities.Patient;
import ro.tuc.ds2020.entities.Caregiver;
import ro.tuc.ds2020.entities.Person;
import ro.tuc.ds2020.repositories.PatientRepository;
import ro.tuc.ds2020.repositories.CaregiverRepository;
import ro.tuc.ds2020.repositories.PersonRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CaregiverService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CaregiverService.class);
    private final CaregiverRepository CaregiverRepository;
    private final PatientRepository PatientRepository;
    private final PersonRepository PersonRepository;

    @Autowired
    public CaregiverService(CaregiverRepository CaregiverRepository, PatientRepository PatientRepository, PersonRepository PersonRepository) {
        this.CaregiverRepository = CaregiverRepository;
        this.PatientRepository = PatientRepository;
        this.PersonRepository = PersonRepository;
    }

    public List<CaregiverDetailsDTO> findCaregivers() {
        List<Caregiver> CaregiverList = CaregiverRepository.findAll();
        return CaregiverList.stream()
                .map(CaregiverBuilder::toCaregiverDTO)
                .collect(Collectors.toList());
    }

    public CaregiverDetailsDTO findCaregiverById(UUID id) {
        Optional<Caregiver> caregiverOptional = CaregiverRepository.findById(id);
        if (!caregiverOptional.isPresent()) {
            LOGGER.error("Caregiver with id {} was not found in db", id);
            throw new ResourceNotFoundException(Caregiver.class.getSimpleName() + " with id: " + id);
        }
        return CaregiverBuilder.toCaregiverDTO(caregiverOptional.get());
    }

    public CaregiverDetailsDTO findCaregiverByFK(UUID id) {
        List<Caregiver> caregivers = CaregiverRepository.findAll();
        ListIterator<Caregiver> iterator = caregivers.listIterator();
        Caregiver caregiver = new Caregiver();
        while (iterator.hasNext()){
            caregiver = iterator.next();
            if(caregiver.getPerson().getId().equals(id)){
                LOGGER.error("Caregiver with id {} was found in db", id);
                return CaregiverBuilder.toCaregiverDTO(caregiver);
            }
        }
        return CaregiverBuilder.toCaregiverDTO(caregiver);
    }

    public UUID insert(CaregiverDetailsDTO caregiverDTO) {
        Caregiver caregiver = CaregiverBuilder.toEntity(caregiverDTO);
        caregiver = CaregiverRepository.save(caregiver);
        LOGGER.debug("Caregiver with id {} was inserted in db", caregiver.getId());
        return caregiver.getId();
    }

    public UUID update(CaregiverDetailsDTO caregiverDTO, UUID id) {
        Optional<Caregiver> caregiverOptional = CaregiverRepository.findById(id);
        if (!caregiverOptional.isPresent()) {
            LOGGER.error("Caregiver with id {} was not found in db");
            throw new ResourceNotFoundException(Caregiver.class.getSimpleName() + " with id ");
        }
        //List<Patient> patients = caregiverOptional.get().getPatients();
        Caregiver caregiver = CaregiverBuilder.toEntity(caregiverDTO);
        //ListIterator<Patient> iterator = patients.listIterator();
        //while (iterator.hasNext()){
//            Patient patient = iterator.next();
//            patients.add(patient);
//        }
        this.CaregiverRepository.deleteById(id);
        caregiver = CaregiverRepository.save(caregiver);
        LOGGER.debug("Caregiver with id {} was deleted from the db", id);
        return caregiver.getId();
    }

    public void delete(UUID id){
        Optional<Caregiver> caregiverOptional = CaregiverRepository.findById(id);
        if (!caregiverOptional.isPresent()) {
            LOGGER.error("Caregiver with id {} was not found in db");
            throw new ResourceNotFoundException(Caregiver.class.getSimpleName() + " with id ");
        }
        this.CaregiverRepository.deleteById(id);
        LOGGER.debug("Caregiver with id {} was deleted from the db", id);
    }

    public void addPatientToCaregiver(UUID id, UUID id_p) {

        Optional<Caregiver> caregiverOptional = CaregiverRepository.findById(id);
        if (!caregiverOptional.isPresent()) {
            LOGGER.error("Caregiver with id {} was not found in db");
            throw new ResourceNotFoundException(Caregiver.class.getSimpleName() + " with id ");
        }
        Optional<Patient> patientOptional = PatientRepository.findById(id_p);
        if (!patientOptional.isPresent()) {
            LOGGER.error("Patient with id {} was not found in db");
            throw new ResourceNotFoundException(Caregiver.class.getSimpleName() + " with id ");
        }

        Caregiver caregiver = caregiverOptional.get();
        caregiver.addPatient(patientOptional.get());
        CaregiverRepository.save(caregiver);
    }

    public List<PatientDetailsDTO> findPatientofCaregiver(UUID id)
    {
        Optional<Caregiver> caregiver  = CaregiverRepository.findById(id);
        if (!caregiver.isPresent()) {
            LOGGER.error("Caregiver with id {} was not found in db");
            throw new ResourceNotFoundException(Patient.class.getSimpleName() + " with id ");
        }
        List<Patient> patients = caregiver.get().getPatients();
        List<PatientDetailsDTO> patientsDTO = PatientBuilder.toPatientDTO(patients);

        return patientsDTO;
    }
}
