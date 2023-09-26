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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
        People person = findByUuid(request.getUuid());

        person.setBirthDate(request.getBirthDate());
        person.setName(request.getName());
        peopleRepository.save(person);
    }

    public People findByUuid(String uuid){
        People person = peopleRepository.findByUuid(uuid);
        if (person == null) {
            throw new BusinessException("Cadastro não encontrado");
        }
        return person;
    }

    public PeopleSelectResponse select(String uuid) {
        People person = findByUuid(uuid);

        PeopleSelectResponse response = new ModelMapper().map(person, PeopleSelectResponse.class);
        return response;
    }

    public Page<PeopleSelectResponse> search(String name, Pageable pageable) {
        Page<People> people = null;
        if (!name.isBlank()) {
            people = peopleRepository.findByNameContaining(name, pageable);
        } else {
            people = peopleRepository.findAll(pageable);
        }
        return new PageImpl<>(convertToResponse(people.getContent()), pageable, pageable.getPageNumber());
    }

    public List<PeopleSelectResponse> convertToResponse(List<People> content) {
        List<PeopleSelectResponse> response = new ArrayList<>();

        ModelMapper modelMapper = new ModelMapper();
        content.forEach(c -> {
            PeopleSelectResponse r = modelMapper.map(c, PeopleSelectResponse.class);
            response.add(r);
        });
        return response;
    }

}
