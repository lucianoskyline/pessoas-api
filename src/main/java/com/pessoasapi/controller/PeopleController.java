package com.pessoasapi.controller;

import com.pessoasapi.request.PeopleCreateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/people")
public class PeopleController {


    @PostMapping
    public void create(@Valid @RequestBody PeopleCreateRequest request){

    }

}
