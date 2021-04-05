package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.DoctorDetailsDTO;
import ro.tuc.ds2020.services.DoctorService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping(value = "/doctor")
public class DoctorController {

    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping()
    public ResponseEntity<List<DoctorDetailsDTO>> getDoctors() {
        List<DoctorDetailsDTO> dtos = doctorService.findDoctors();
        for (DoctorDetailsDTO dto : dtos) {
            Link DoctorLink = linkTo(methodOn(DoctorController.class)
                    .getDoctor(dto.getId())).withRel("DoctorDetails");
            dto.add(DoctorLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UUID> insertDoctor(@Valid @RequestBody DoctorDetailsDTO doctorDTO) {
        UUID doctorID = doctorService.insert(doctorDTO);
        return new ResponseEntity<>(doctorID, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DoctorDetailsDTO> getDoctor(@PathVariable("id") UUID doctorId) {
        DoctorDetailsDTO dto = doctorService.findDoctorById(doctorId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //TODO: UPDATE, DELETE per resource

    @PutMapping(value = "/{id}")
    public ResponseEntity<UUID> updateDoctor(@RequestBody DoctorDetailsDTO doctorDTO, @PathVariable("id") UUID doctorId) {
        UUID doctorID = doctorService.update(doctorDTO, doctorId);
        return new ResponseEntity<>(doctorID, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UUID> deleteDoctor(@PathVariable UUID id) {
        doctorService.delete(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
