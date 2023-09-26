package com.pessoasapi.controller;

import com.pessoasapi.exception.BusinessException;
import com.pessoasapi.exception.ExceptionResponse;
import com.pessoasapi.request.PeopleCreateRequest;
import com.pessoasapi.request.PeopleSearchRequest;
import com.pessoasapi.request.PeopleUpdateRequest;
import com.pessoasapi.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping
    public ResponseEntity update(@Valid @RequestBody PeopleUpdateRequest request) {
        try {
            peopleService.update(request);
            return ResponseEntity.ok().build();
        } catch (BusinessException ex) {
            return ResponseEntity.badRequest().body(new ExceptionResponse(ex.getMessage()));
        }
    }

    @GetMapping("/{uuid}")
    public ResponseEntity select(@PathVariable String uuid) {
        try {
            return ResponseEntity.ok(peopleService.select(uuid));
        } catch (BusinessException ex) {
            return ResponseEntity.badRequest().body(new ExceptionResponse(ex.getMessage()));
        }
    }

    @PostMapping("/search")
    public ResponseEntity search(@Valid @RequestBody PeopleSearchRequest request, Pageable pageable) {
        try {
            return ResponseEntity.ok(peopleService.search(request.getName(), pageable));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new ExceptionResponse(ex.getMessage()));
        }
    }

}
