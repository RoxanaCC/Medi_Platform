package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.PatientDetailsDTO;
import ro.tuc.ds2020.dtos.CaregiverDetailsDTO;
import ro.tuc.ds2020.services.CaregiverService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping(value = "/caregiver")
public class CaregiverController {

    private final CaregiverService caregiverService;

    @Autowired
    public CaregiverController(CaregiverService caregiverService) {
        this.caregiverService = caregiverService;
    }

    @GetMapping()
    public ResponseEntity<List<CaregiverDetailsDTO>> getCaregivers() {
        List<CaregiverDetailsDTO> dtos = caregiverService.findCaregivers();
        for (CaregiverDetailsDTO dto : dtos) {
            Link CaregiverLink = linkTo(methodOn(CaregiverController.class)
                    .getCaregiver(dto.getId())).withRel("CaregiverDetails");
            dto.add(CaregiverLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UUID> insertCaregiver(@Valid @RequestBody CaregiverDetailsDTO caregiverDTO) {
        UUID CaregiverID = caregiverService.insert(caregiverDTO);
        return new ResponseEntity<>(CaregiverID, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CaregiverDetailsDTO> getCaregiver(@PathVariable("id") UUID caregiverId) {
        CaregiverDetailsDTO dto = caregiverService.findCaregiverById(caregiverId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/fkey")
    public ResponseEntity<CaregiverDetailsDTO> getCaregiverByFK(@PathVariable("id") UUID caregiverId) {
        CaregiverDetailsDTO dto = caregiverService.findCaregiverByFK(caregiverId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //TODO: UPDATE, DELETE per resource

    @PutMapping(value = "/{id}")
    public ResponseEntity<UUID> updateCaregiver(@RequestBody CaregiverDetailsDTO caregiverDTO, @PathVariable("id") UUID caregiverId) {
        UUID CaregiverID = caregiverService.update(caregiverDTO, caregiverId);
        return new ResponseEntity<>(CaregiverID, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UUID> deleteCaregiver(@PathVariable UUID id) {
        caregiverService.delete(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/patient/{id_p}")
    public ResponseEntity<UUID> addPatientToCaregiver(@PathVariable("id") UUID id,@PathVariable("id_p") UUID id_p){
        caregiverService.addPatientToCaregiver(id,id_p);
        return new ResponseEntity<>(id_p, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/patient")
    public ResponseEntity<List<PatientDetailsDTO>> findPatientofCaregiver(@PathVariable("id") UUID id){
        List<PatientDetailsDTO> dtos = caregiverService.findPatientofCaregiver(id);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
}
