package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.MedicationDTO;
import ro.tuc.ds2020.dtos.builders.MedicationBuilder;
import ro.tuc.ds2020.entities.Medication;
import ro.tuc.ds2020.repositories.MedicationRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MedicationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MedicationService.class);
    private final MedicationRepository MedicationRepository;

    @Autowired
    public MedicationService(MedicationRepository MedicationRepository) {
        this.MedicationRepository = MedicationRepository;
    }

    public List<MedicationDTO> findMedications() {
        List<Medication> medicationList = MedicationRepository.findAll();
        return medicationList.stream()
                .map(MedicationBuilder::toMedicationDTO)
                .collect(Collectors.toList());
    }

    public MedicationDTO findMedicationById(UUID id) {
        Optional<Medication> medicationOptional = MedicationRepository.findById(id);
        if (!medicationOptional.isPresent()) {
            LOGGER.error("Medication with id {} was not found in db", id);
            throw new ResourceNotFoundException(Medication.class.getSimpleName() + " with id: " + id);
        }
        return MedicationBuilder.toMedicationDTO(medicationOptional.get());
    }

    public UUID insert(MedicationDTO MedicationDTO) {
        Medication medication = MedicationBuilder.toEntity(MedicationDTO);
        medication = MedicationRepository.save(medication);
        LOGGER.debug("Medication with id {} was inserted in db", medication.getId());
        return medication.getId();
    }

    public UUID update(MedicationDTO medicationDTO, UUID id) {
        Optional<Medication> medicationOptional = MedicationRepository.findById(id);
        if (!medicationOptional.isPresent()) {
            LOGGER.error("Medication with id {} was not found in db");
            throw new ResourceNotFoundException(Medication.class.getSimpleName() + " with id ");
        }
        Medication medication = MedicationBuilder.toEntity(medicationDTO);
        medication.setId(id);
        medicationDTO.setId(id);
        medication = MedicationRepository.save(medication);
        LOGGER.debug("Medication with id {} was updated in db", medication.getId());
        return medication.getId();
    }

    public void delete(UUID id){
        Optional<Medication> medicationOptional = MedicationRepository.findById(id);
        if (!medicationOptional.isPresent()) {
            LOGGER.error("Medication with id {} was not found in db");
            throw new ResourceNotFoundException(Medication.class.getSimpleName() + " with id ");
        }
        this.MedicationRepository.deleteById(id);
        LOGGER.debug("Medication with id {} was deleted from the db", id);
    }
}
