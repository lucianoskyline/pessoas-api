package com.pessoasapi.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PeopleAddressUpdateRequest {

    @NotBlank(message = "Informe o identificador do cadastro")
    private String uuid;

    private Boolean principal;

}
