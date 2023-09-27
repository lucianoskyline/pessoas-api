package com.pessoasapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pessoasapi.request.PeopleAddressCreateRequest;
import com.pessoasapi.request.PeopleCreateRequest;
import com.pessoasapi.request.PeopleSearchRequest;
import com.pessoasapi.request.PeopleUpdateRequest;
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
public class PeopleControllerTest {

    @Autowired
    private MockMvc mockMvc;


    public PeopleSelectResponse createDefault() throws Exception {
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


    @Test
    public void create() throws Exception {
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

        mockMvc.perform(post("/people")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());
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
    public void getByUuid() throws Exception {
        PeopleSelectResponse response = createDefault();
        mockMvc.perform(get("/people/" + response.getUuid())).andExpect(status().isOk());
    }

    @Test
    public void search() throws Exception {
        PeopleSelectResponse responseCreate = createDefault();
        PeopleSearchRequest request = new PeopleSearchRequest();
        request.setName(responseCreate.getName());
        mockMvc.perform(post("/people/search")
                .contentType("application/json")
                .content(new ObjectMapper().writeValueAsString(request))).andExpect(status().isOk());
    }

    @Test
    public void update() throws Exception {
        PeopleSelectResponse response = createDefault();
        PeopleUpdateRequest request=new ModelMapper().map(response, PeopleUpdateRequest.class);
        request.setName("Nome atualizado");
        mockMvc.perform(put("/people")
                .contentType("application/json")
                .content(new ObjectMapper().writeValueAsString(request))).andExpect(status().isOk());
    }

    @Test
    public void updateNonExistentUuid() throws Exception {
        PeopleSelectResponse response = createDefault();
        PeopleUpdateRequest request=new ModelMapper().map(response, PeopleUpdateRequest.class);
        request.setName("Nome atualizado");
        request.setUuid(UUID.randomUUID().toString());
        mockMvc.perform(put("/people")
                .contentType("application/json")
                .content(new ObjectMapper().writeValueAsString(request))).andExpect(status().isBadRequest());
    }

}
