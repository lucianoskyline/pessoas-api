package com.pessoasapi.service;

import com.pessoasapi.model.People;
import com.pessoasapi.model.PeopleAddress;
import com.pessoasapi.repository.PeopleAddressRepository;
import com.pessoasapi.repository.PeopleRepository;
import com.pessoasapi.request.PeopleCreateRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PeopleService {

    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private PeopleAddressRepository peopleAddressRepository;

    public void create(PeopleCreateRequest request) {
        ModelMapper modelMapper = new ModelMapper();
        People person = modelMapper.map(request, People.class);
        person.setUuid(UUID.randomUUID().toString());

        PeopleAddress personAddress = modelMapper.map(request.getAddress(), PeopleAddress.class);
        personAddress.setPrincipal(true);
        personAddress.setUuid(UUID.randomUUID().toString());
        personAddress.setPerson(peopleRepository.save(person));
        peopleAddressRepository.save(personAddress);
    }

}
