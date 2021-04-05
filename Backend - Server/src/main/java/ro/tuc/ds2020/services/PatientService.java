package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.CaregiverDetailsDTO;
import ro.tuc.ds2020.dtos.MedPlanDetailsDTO;
import ro.tuc.ds2020.dtos.PatientDetailsDTO;
import ro.tuc.ds2020.dtos.builders.CaregiverBuilder;
import ro.tuc.ds2020.dtos.builders.MedPlanBuilder;
import ro.tuc.ds2020.dtos.builders.PatientBuilder;
import ro.tuc.ds2020.entities.Caregiver;
import ro.tuc.ds2020.entities.MedPlan;
import ro.tuc.ds2020.entities.Patient;
import ro.tuc.ds2020.entities.Person;
import ro.tuc.ds2020.repositories.MedPlanRepository;
import ro.tuc.ds2020.repositories.PatientRepository;
import ro.tuc.ds2020.repositories.PersonRepository;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PatientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientService.class);
    private final PatientRepository PatientRepository;
    private final MedPlanRepository MedPlanRepository;
    private final PersonRepository PersonRepository;

    @Autowired
    public PatientService(PatientRepository PatientRepository, MedPlanRepository MedPlanRepository, PersonRepository PersonRepository) {
        this.PatientRepository = PatientRepository;
        this.MedPlanRepository = MedPlanRepository;
        this.PersonRepository = PersonRepository;
    }

    public List<PatientDetailsDTO> findPatients() {
        List<Patient> patientList = PatientRepository.findAll();
        return patientList.stream()
                .map(PatientBuilder::toPatientDTO)
                .collect(Collectors.toList());
    }

    public PatientDetailsDTO findPatientById(UUID id) {
        Optional<Patient> patientOptional = PatientRepository.findById(id);
        if (!patientOptional.isPresent()) {
            LOGGER.error("Patient with id {} was not found in db", id);
            throw new ResourceNotFoundException(Patient.class.getSimpleName() + " with id: " + id);
        }
        return PatientBuilder.toPatientDTO(patientOptional.get());
    }

    public PatientDetailsDTO findPatientByFK(UUID id) {
        List<Patient> patients = PatientRepository.findAll();
        ListIterator<Patient> iterator = patients.listIterator();
        Patient patient = new Patient();
        while (iterator.hasNext()){
            patient = iterator.next();
            if(patient.getPerson().getId().equals(id)){
                return PatientBuilder.toPatientDTO(patient);
            }
        }
        return PatientBuilder.toPatientDTO(patient);
    }

    public UUID insert(PatientDetailsDTO patientDTO) {
        Patient patient = PatientBuilder.toEntity(patientDTO);
        patient = PatientRepository.save(patient);
        LOGGER.debug("Patient with id {} was inserted in db", patient.getId());
        return patient.getId();
    }

    public UUID update(PatientDetailsDTO patientDTO, UUID id) {
        Optional<Patient> patientOptional = PatientRepository.findById(id);
        if (!patientOptional.isPresent()) {
            LOGGER.error("Patient with id {} was not found in db");
            throw new ResourceNotFoundException(Patient.class.getSimpleName() + " with id ");
        }
        Patient patient = PatientBuilder.toEntity(patientDTO);
        this.PatientRepository.deleteById(id);
        patient = PatientRepository.save(patient);
        LOGGER.debug("Patient with id {} was deleted from the db", id);
//        Patient patient = PatientBuilder.toEntity(patientDTO);
//        patient.setId(id);
//        patientDTO.setId(id);
//        patient.getPerson().setId(patientOptional.get().getPerson().getId());
//        patient = PatientRepository.save(patient);
//        LOGGER.debug("Patient with id {} was updated in db", patient.getId());
        return patient.getId();
    }

    public void delete(UUID id){
        Optional<Patient> patientOptional = PatientRepository.findById(id);
        if (!patientOptional.isPresent()) {
            LOGGER.error("Patient with id {} was not found in db");
            throw new ResourceNotFoundException(Patient.class.getSimpleName() + " with id ");
        }
        this.PatientRepository.deleteById(id);
        LOGGER.debug("Patient with id {} was deleted from the db", id);
    }

    public void addMedPlanToPatient(UUID id, UUID id_mp) {

        Optional<Patient> patientOptional = PatientRepository.findById(id);
        if (!patientOptional.isPresent()) {
            LOGGER.error("Patient with id {} was not found in db");
            throw new ResourceNotFoundException(Patient.class.getSimpleName() + " with id ");
        }
        Optional<MedPlan> medPlanOptional = MedPlanRepository.findById(id_mp);
        if (!medPlanOptional.isPresent()) {
            LOGGER.error("MedPlan with id {} was not found in db");
            throw new ResourceNotFoundException(Patient.class.getSimpleName() + " with id ");
        }

        Patient patient = patientOptional.get();
        patient.addMedicationPlan(medPlanOptional.get());
        PatientRepository.save(patient);
    }

    public List<MedPlanDetailsDTO> findMedPlanofPatient(UUID id)
    {
        Optional<Patient> patient  = PatientRepository.findById(id);
        if (!patient.isPresent()) {
            LOGGER.error("Patient with id {} was not found in db");
            throw new ResourceNotFoundException(Patient.class.getSimpleName() + " with id ");
        }
        List<MedPlan> plans = patient.get().getMedPlans();
        List<MedPlanDetailsDTO> medplansDTO = MedPlanBuilder.toMedPlanDTO(plans);

        return medplansDTO;
    }
}
