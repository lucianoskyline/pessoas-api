package com.pessoasapi.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.Date;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class PeopleSelectResponse {

    private String uuid;

    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT-3")
    private Date birthDate;

}
