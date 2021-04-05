package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.MedPlanDetailsDTO;
import ro.tuc.ds2020.dtos.builders.MedPlanBuilder;
import ro.tuc.ds2020.entities.MedPlan;
import ro.tuc.ds2020.entities.Medication;
import ro.tuc.ds2020.entities.Patient;
import ro.tuc.ds2020.repositories.MedPlanRepository;
import ro.tuc.ds2020.repositories.MedicationRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MedPlanService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MedPlanService.class);
    private final MedPlanRepository MedPlanRepository;
    private final MedicationRepository MedicationRepository;

    @Autowired
    public MedPlanService(MedPlanRepository MedPlanRepository, MedicationRepository MedicationRepository) {
        this.MedPlanRepository = MedPlanRepository;
        this.MedicationRepository = MedicationRepository;
    }

    public List<MedPlanDetailsDTO> findMedPlans() {
        List<MedPlan> medPlanList = MedPlanRepository.findAll();
        return medPlanList.stream()
                .map(MedPlanBuilder::toMedPlanDTO)
                .collect(Collectors.toList());
    }

    public MedPlanDetailsDTO findMedPlanById(UUID id) {
        Optional<MedPlan> medPlanOptional = MedPlanRepository.findById(id);
        if (!medPlanOptional.isPresent()) {
            LOGGER.error("MedPlan with id {} was not found in db", id);
            throw new ResourceNotFoundException(MedPlan.class.getSimpleName() + " with id: " + id);
        }
        return MedPlanBuilder.toMedPlanDTO(medPlanOptional.get());
    }

    public UUID insert(MedPlanDetailsDTO MedPlanDTO) {
        MedPlan medPlan = MedPlanBuilder.toEntity(MedPlanDTO);
        medPlan = MedPlanRepository.save(medPlan);
        LOGGER.debug("MedPlan with id {} was inserted in db", medPlan.getId());
        return medPlan.getId();
    }

    public void delete(UUID id){
        Optional<MedPlan> medPlanOptional = MedPlanRepository.findById(id);
        if (!medPlanOptional.isPresent()) {
            LOGGER.error("MedPlan with id {} was not found in db");
            throw new ResourceNotFoundException(MedPlan.class.getSimpleName() + " with id ");
        }
        this.MedPlanRepository.deleteById(id);
        LOGGER.debug("MedPlan with id {} was deleted from the db", id);
    }

    public void addMedicationToMedPlan(UUID id, UUID id_m) {

        Optional<MedPlan> medPlanOptional = MedPlanRepository.findById(id);
        if (!medPlanOptional.isPresent()) {
            LOGGER.error("MedPlan with id {} was not found in db");
            throw new ResourceNotFoundException(MedPlan.class.getSimpleName() + " with id ");
        }
        Optional<Medication> medOptional = MedicationRepository.findById(id_m);
        if (!medOptional.isPresent()) {
            LOGGER.error("Medication with id {} was not found in db");
            throw new ResourceNotFoundException(MedPlan.class.getSimpleName() + " with id ");
        }

        MedPlan medPlan = medPlanOptional.get();
        medPlan.addMedication(medOptional.get());
        MedPlanRepository.save(medPlan);
    }
}
