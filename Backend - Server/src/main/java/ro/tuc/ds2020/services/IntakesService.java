package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.entities.Intakes;
import ro.tuc.ds2020.repositories.IntakesRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class IntakesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);
    private final IntakesRepository IntakesRepository;

    @Autowired
    public IntakesService(IntakesRepository personRepository) {
        this.IntakesRepository = personRepository;
    }

    public List<Intakes> findIntakes() {
        List<Intakes> intakeList = IntakesRepository.findAll();
        return intakeList;
    }

    public Intakes findIntakesById(UUID id) {
        Optional<Intakes> intakeOptional = IntakesRepository.findById(id);
        if (!intakeOptional.isPresent()) {
            LOGGER.error("Sensor Data with id {} was not found in db", id);
            throw new ResourceNotFoundException(Intakes.class.getSimpleName() + " with id: " + id);
        }
        return intakeOptional.get();
    }

    public UUID insert(Intakes intake) {
        intake = IntakesRepository.save(intake);
        LOGGER.debug("Sensor Data with id {} was inserted in db", intake.getId());
        return intake.getId();
    }
}
