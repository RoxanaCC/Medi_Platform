package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.SensorDataDTO;
import ro.tuc.ds2020.dtos.builders.SensorDataBuilder;
import ro.tuc.ds2020.entities.SensorData;
import ro.tuc.ds2020.repositories.SensorDataRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SensorDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);
    private final SensorDataRepository sensorDataRepository;

    @Autowired
    public SensorDataService(SensorDataRepository personRepository) {
        this.sensorDataRepository = personRepository;
    }

    public List<SensorDataDTO> findSensorData() {
        List<SensorData> personList = sensorDataRepository.findAll();
        return personList.stream()
                .map(SensorDataBuilder::toSensorDataDTO)
                .collect(Collectors.toList());
    }

    public SensorDataDTO findSensorDataById(UUID id) {
        Optional<SensorData> sensorOptional = sensorDataRepository.findById(id);
        if (!sensorOptional.isPresent()) {
            LOGGER.error("Sensor Data with id {} was not found in db", id);
            throw new ResourceNotFoundException(SensorData.class.getSimpleName() + " with id: " + id);
        }
        return SensorDataBuilder.toSensorDataDTO(sensorOptional.get());
    }

    public UUID insert(SensorDataDTO sensorDataDTO) {
        SensorData sensorData = SensorDataBuilder.toEntity(sensorDataDTO);
        sensorData = sensorDataRepository.save(sensorData);
        LOGGER.debug("Sensor Data with id {} was inserted in db", sensorData.getId());
        return sensorData.getId();
    }
}
