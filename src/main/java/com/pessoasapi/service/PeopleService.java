package com.pessoasapi.service;

import com.pessoasapi.exception.BusinessException;
import com.pessoasapi.model.People;
import com.pessoasapi.model.PeopleAddress;
import com.pessoasapi.repository.PeopleAddressRepository;
import com.pessoasapi.repository.PeopleRepository;
import com.pessoasapi.request.PeopleCreateRequest;
import com.pessoasapi.request.PeopleUpdateRequest;
import com.pessoasapi.response.PeopleSelectResponse;
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

    public void update(PeopleUpdateRequest request) {
        People person = peopleRepository.findByUuid(request.getUuid());
        if (person == null) {
            throw new BusinessException("Cadastro não encontrado");
        }

        person.setBirthDate(request.getBirthDate());
        person.setName(request.getName());
        peopleRepository.save(person);
    }

    public PeopleSelectResponse select(String uuid) {
        People person = peopleRepository.findByUuid(uuid);
        if (person == null) {
            throw new BusinessException("Cadastro não encontrado");
        }

        PeopleSelectResponse response = new ModelMapper().map(person, PeopleSelectResponse.class);
        return response;
    }

}
