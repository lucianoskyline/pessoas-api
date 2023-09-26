package com.pessoasapi.service;

import com.pessoasapi.exception.BusinessException;
import com.pessoasapi.model.People;
import com.pessoasapi.model.PeopleAddress;
import com.pessoasapi.repository.PeopleAddressRepository;
import com.pessoasapi.repository.PeopleRepository;
import com.pessoasapi.request.PeopleAddressCreateRequest;
import com.pessoasapi.response.PeopleAddressResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PeopleAddressService {

    @Autowired
    private PeopleAddressRepository peopleAddressRepository;

    @Autowired
    private PeopleRepository peopleRepository;


    public void create(PeopleAddressCreateRequest request) {
        if (request.getUuid().isBlank()) {
            throw new BusinessException("Informe o identificador da pessoa");
        }

        People person = peopleRepository.findByUuid(request.getUuid());
        if (person == null) {
            throw new BusinessException("Cadastro não encontrado");
        }

        ModelMapper modelMapper = new ModelMapper();
        PeopleAddress personAddress = modelMapper.map(request, PeopleAddress.class);
        personAddress.setUuid(UUID.randomUUID().toString());
        personAddress.setPerson(person);
        personAddress = peopleAddressRepository.save(personAddress);
        if(personAddress.getPrincipal()){
            disableOthersPrincipal(personAddress);
        }
    }

    public void disableOthersPrincipal(PeopleAddress peopleAddress) {
        List<PeopleAddress> address = peopleAddressRepository.findByPerson(peopleAddress.getPerson());
        address.forEach(a -> {
            if (a.getId() != peopleAddress.getId()) {
                a.setPrincipal(false);
                peopleAddressRepository.save(a);
            }
        });
    }

    public List<PeopleAddressResponse> search(String uuid) {
        People person = peopleRepository.findByUuid(uuid);
        if (person == null) {
            throw new BusinessException("Cadastro não encontrado");
        }
        List<PeopleAddress> address = peopleAddressRepository.findByPerson(person);
        return convertToResponse(address);
    }

    public List<PeopleAddressResponse> convertToResponse(List<PeopleAddress> content) {
        List<PeopleAddressResponse> response = new ArrayList<>();

        ModelMapper modelMapper = new ModelMapper();
        content.forEach(c -> {
            PeopleAddressResponse r = modelMapper.map(c, PeopleAddressResponse.class);
            response.add(r);
        });
        return response;
    }

}
