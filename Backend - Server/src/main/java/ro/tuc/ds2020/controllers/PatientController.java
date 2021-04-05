package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.MedPlanDetailsDTO;
import ro.tuc.ds2020.dtos.PatientDetailsDTO;
import ro.tuc.ds2020.services.PatientService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping(value = "/patient")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping()
    public ResponseEntity<List<PatientDetailsDTO>> getPatients() {
        List<PatientDetailsDTO> dtos = patientService.findPatients();
        for (PatientDetailsDTO dto : dtos) {
            Link PatientLink = linkTo(methodOn(PatientController.class)
                    .getPatient(dto.getId())).withRel("PatientDetails");
            dto.add(PatientLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UUID> insertPatient(@Valid @RequestBody PatientDetailsDTO patientDTO) {
        UUID patientID = patientService.insert(patientDTO);
        return new ResponseEntity<>(patientID, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PatientDetailsDTO> getPatient(@PathVariable("id") UUID patientId) {
        PatientDetailsDTO dto = patientService.findPatientById(patientId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/fkey")
    public ResponseEntity<PatientDetailsDTO> getPatientByFK(@PathVariable("id") UUID patientId) {
        PatientDetailsDTO dto = patientService.findPatientByFK(patientId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //TODO: UPDATE, DELETE per resource

    @PutMapping(value = "/{id}")
    public ResponseEntity<UUID> updatePatient(@RequestBody PatientDetailsDTO patientDTO, @PathVariable("id") UUID patientId) {
        UUID patientID = patientService.update(patientDTO, patientId);
        return new ResponseEntity<>(patientID, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UUID> deletePatient(@PathVariable UUID id) {
        patientService.delete(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/medplan/{id_mp}")
    public ResponseEntity<UUID> addMedPlanToPatient(@PathVariable("id") UUID id,@PathVariable("id_mp") UUID id_mp){
        patientService.addMedPlanToPatient(id,id_mp);
        return new ResponseEntity<>(id_mp, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/medplan")
    public ResponseEntity<List<MedPlanDetailsDTO>> findMedPlanofPatient(@PathVariable("id") UUID id){
        List<MedPlanDetailsDTO> dtos = patientService.findMedPlanofPatient(id);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
}
