package com.pessoasapi.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class PeopleCreateRequest {

    @NotBlank(message = "Informe o nome corretamente")
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @NotNull(message = "Informe um endereço para cadastro")
    private PeopleAddressCreateRequest address;

}
