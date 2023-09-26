package com.pessoasapi.controller;

import com.pessoasapi.exception.ExceptionResponse;
import com.pessoasapi.request.PeopleCreateRequest;
import com.pessoasapi.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PeopleService peopleService;

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody PeopleCreateRequest request) {
        try {
            peopleService.create(request);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new ExceptionResponse(ex.getMessage()));
        }
    }

}
