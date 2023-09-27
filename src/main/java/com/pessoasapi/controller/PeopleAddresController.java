package com.pessoasapi.controller;

import com.pessoasapi.exception.BusinessException;
import com.pessoasapi.exception.ExceptionResponse;
import com.pessoasapi.request.PeopleAddressCreateRequest;
import com.pessoasapi.request.PeopleAddressUpdateRequest;
import com.pessoasapi.service.PeopleAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/people-address")
public class PeopleAddresController {

    @Autowired
    private PeopleAddressService peopleAddressService;


    @PostMapping
    public ResponseEntity create(@Valid @RequestBody PeopleAddressCreateRequest request) {
        try {
            return ResponseEntity.ok(peopleAddressService.create(request));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new ExceptionResponse(ex.getMessage()));
        }
    }

    @GetMapping("/search/{uuid}")
    public ResponseEntity select(@PathVariable String uuid) {
        try {
            return ResponseEntity.ok(peopleAddressService.search(uuid));
        } catch (BusinessException ex) {
            return ResponseEntity.badRequest().body(new ExceptionResponse(ex.getMessage()));
        }
    }

    @PutMapping
    public ResponseEntity update(@Valid @RequestBody PeopleAddressUpdateRequest request) {
        try {
            peopleAddressService.update(request);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new ExceptionResponse(ex.getMessage()));
        }
    }

}
