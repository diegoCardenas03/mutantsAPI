package com.mutants.mutantsAPI.controllers;

import com.mutants.mutantsAPI.dtos.MutantsDto;
import com.mutants.mutantsAPI.services.MutantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/mutant")
@CrossOrigin(origins = "*")
public class MutantsController {
    private MutantsService mutanteService;
    @Autowired
    public MutantsController(MutantsService mutanteService){
        this.mutanteService = mutanteService;
    }

    @PostMapping("")
    public ResponseEntity<?> isMutant(@RequestBody MutantsDto mutantePruebaDto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(mutanteService.isMutant(mutantePruebaDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}