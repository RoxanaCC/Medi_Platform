package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.services.IntakesService;
import ro.tuc.ds2020.entities.Intakes;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value = "/intakes")
public class IntakesController {
    private final IntakesService IntakesService;

    @Autowired
    public IntakesController(IntakesService IntakesService) {
        this.IntakesService = IntakesService;
    }

    @GetMapping()
    public ResponseEntity<List<Intakes>> getIntakes() {
        List<Intakes> intakes = IntakesService.findIntakes();
        return new ResponseEntity<>(intakes, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Intakes> getIntakes(@PathVariable("id") UUID IntakesId) {
        Intakes intake = IntakesService.findIntakesById(IntakesId);
        return new ResponseEntity<>(intake, HttpStatus.OK);
    }
}
