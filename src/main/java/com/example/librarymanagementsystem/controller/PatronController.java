package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.dto.PatronCreateInfo;
import com.example.librarymanagementsystem.dto.PatronDto;
import com.example.librarymanagementsystem.exception.BaseValidationException;
import com.example.librarymanagementsystem.service.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/patrons")
public class PatronController {

    @Autowired
    private PatronService patronService;

    @GetMapping
    public List<PatronDto> findAll() {
        return patronService.findAllPatrons();
    }

    @GetMapping(value = "/{id}")
    public PatronDto findPatron(@PathVariable Long id) throws BaseValidationException {
        return patronService.findPatron(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<PatronDto> createBook(@RequestBody PatronCreateInfo patronCreateInfo) throws BaseValidationException {
        return ResponseEntity.status(HttpStatus.CREATED).body(patronService.createPatron(patronCreateInfo));
    }

    @PutMapping(value = "/{id}")
    public PatronDto updateBook(@PathVariable Long id, @RequestBody PatronCreateInfo patronCreateInfo) throws BaseValidationException {
        return patronService.updatePatron(id, patronCreateInfo);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBook(@PathVariable Long id) throws BaseValidationException {
        patronService.deletePatron(id);
    }
}
