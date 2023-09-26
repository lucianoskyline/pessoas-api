package com.pessoasapi.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PeopleAddressCreateRequest {

    private String person;

    @NotBlank(message = "Informe o endereço")
    private String address;

    @NotBlank(message = "Informe o CEP")
    private String postalcode;

    @NotBlank(message = "Informe o número")
    private String number;

    @NotBlank(message = "Informe a cidade")
    private String city;

    private Boolean principal;

}
