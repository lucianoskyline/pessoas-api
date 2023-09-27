package com.pessoasapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pessoasapi.request.*;
import com.pessoasapi.response.PeopleAddressResponse;
import com.pessoasapi.response.PeopleSelectResponse;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PeopleAddressControllerTest {

    @Autowired
    private MockMvc mockMvc;


    public PeopleSelectResponse createPersonDefault() throws Exception {
        PeopleCreateRequest request = new PeopleCreateRequest();
        request.setName("Nome teste");
        request.setBirthDate(new Date());

        PeopleAddressCreateRequest requestAddress = new PeopleAddressCreateRequest();
        requestAddress.setAddress("Endereço teste");
        requestAddress.setPrincipal(true);
        requestAddress.setCity("Cidade teste");
        requestAddress.setPostalcode("123456");
        requestAddress.setNumber("123");
        request.setAddress(requestAddress);

        PeopleSelectResponse response = new ObjectMapper().readValue(mockMvc.perform(post("/people")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(request))).andReturn().getResponse().getContentAsString(),
                PeopleSelectResponse.class);
        return response;
    }

    public PeopleAddressResponse createAddressDefault() throws Exception {
        PeopleAddressCreateRequest request = new PeopleAddressCreateRequest();
        request.setAddress("Endereço teste");
        request.setPrincipal(true);
        request.setCity("Cidade teste");
        request.setPostalcode("123456");
        request.setNumber("123");
        request.setPerson(createPersonDefault().getUuid());

        PeopleAddressResponse response = new ObjectMapper().readValue(
                mockMvc.perform(post("/people-address")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(request))).andReturn().getResponse().getContentAsString(),
                PeopleAddressResponse.class);
        return response;
    }

    @Test
    public void create() throws Exception {
        PeopleAddressCreateRequest request = new PeopleAddressCreateRequest();
        request.setAddress("Endereço teste");
        request.setPrincipal(true);
        request.setCity("Cidade teste");
        request.setPostalcode("123456");
        request.setNumber("123");
        request.setPerson(createPersonDefault().getUuid());

        mockMvc.perform(post("/people-address")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void createWithoutPerson() throws Exception {
        PeopleAddressCreateRequest request = new PeopleAddressCreateRequest();
        request.setAddress("Endereço teste");
        request.setPrincipal(true);
        request.setCity("Cidade teste");
        request.setPostalcode("123456");
        request.setNumber("123");

        mockMvc.perform(post("/people-address")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createWithoutAddress() throws Exception {
        PeopleCreateRequest request = new PeopleCreateRequest();
        request.setName("Nome teste");
        request.setBirthDate(new Date());

        mockMvc.perform(post("/people")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void search() throws Exception {
        PeopleSelectResponse responseCreate = createPersonDefault();
        mockMvc.perform(get("/people-address/search/" + responseCreate.getUuid())).andExpect(status().isOk());
    }

    @Test
    public void searchNonExistentPerson() throws Exception {
        mockMvc.perform(get("/people-address/search/" + UUID.randomUUID())).andExpect(status().isBadRequest());
    }

    @Test
    public void update() throws Exception {
        PeopleAddressResponse response = createAddressDefault();
        PeopleAddressUpdateRequest request = new ModelMapper().map(response, PeopleAddressUpdateRequest.class);
        request.setPrincipal(true);
        request.setUuid(response.getUuid());

        mockMvc.perform(put("/people-address")
                .contentType("application/json")
                .content(new ObjectMapper().writeValueAsString(request))).andExpect(status().isOk());
    }

    @Test
    public void updateNonExistentUuid() throws Exception {
        PeopleAddressUpdateRequest request = new PeopleAddressUpdateRequest();
        request.setPrincipal(true);
        request.setUuid(UUID.randomUUID().toString());

        mockMvc.perform(put("/people-address")
                .contentType("application/json")
                .content(new ObjectMapper().writeValueAsString(request))).andExpect(status().isBadRequest());
    }

}
