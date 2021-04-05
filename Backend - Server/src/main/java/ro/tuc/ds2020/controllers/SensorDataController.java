package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.SensorDataDTO;
import ro.tuc.ds2020.services.SensorDataService;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping(value = "/sensordata")
public class SensorDataController {
    private final SensorDataService sensorDataService;

    @Autowired
    public SensorDataController(SensorDataService SensorDataService) {
        this.sensorDataService = SensorDataService;
    }

    @GetMapping()
    public ResponseEntity<List<SensorDataDTO>> getSensorDatas() {
        List<SensorDataDTO> dtos = sensorDataService.findSensorData();
        for (SensorDataDTO dto : dtos) {
            Link SensorDataLink = linkTo(methodOn(SensorDataController.class)
                    .getSensorData(dto.getId())).withRel("SensorDataDetails");
            dto.add(SensorDataLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SensorDataDTO> getSensorData(@PathVariable("id") UUID sensorDataId) {
        SensorDataDTO dto = sensorDataService.findSensorDataById(sensorDataId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
