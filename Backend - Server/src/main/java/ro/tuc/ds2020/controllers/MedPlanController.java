package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.MedPlanDetailsDTO;
import ro.tuc.ds2020.services.MedPlanService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping(value = "/medplan")
public class MedPlanController {

    private final MedPlanService medPlanService;

    @Autowired
    public MedPlanController(MedPlanService medPlanService) {
        this.medPlanService = medPlanService;
    }

    @GetMapping()
    public ResponseEntity<List<MedPlanDetailsDTO>> getMedPlans() {
        List<MedPlanDetailsDTO> dtos = medPlanService.findMedPlans();
        for (MedPlanDetailsDTO dto : dtos) {
            Link MedPlanLink = linkTo(methodOn(MedPlanController.class)
                    .getMedPlan(dto.getId())).withRel("MedPlanDetails");
            dto.add(MedPlanLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UUID> insertMedPlan(@Valid @RequestBody MedPlanDetailsDTO medPlanDTO) {
        UUID medPlanID = medPlanService.insert(medPlanDTO);
        return new ResponseEntity<>(medPlanID, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MedPlanDetailsDTO> getMedPlan(@PathVariable("id") UUID medPlanId) {
        MedPlanDetailsDTO dto = medPlanService.findMedPlanById(medPlanId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //TODO: UPDATE, DELETE per resource


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UUID> deleteMedPlan(@PathVariable UUID id) {
        medPlanService.delete(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/medication/{id_m}")
    public ResponseEntity<UUID> addMedicationToMedicationPlan(@PathVariable("id") UUID id,@PathVariable("id_m") UUID id_m){
        medPlanService.addMedicationToMedPlan(id,id_m);
        return new ResponseEntity<>(id_m, HttpStatus.OK);
    }
}
