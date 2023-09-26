package com.pessoasapi.response;

import lombok.Data;

@Data
public class PeopleAddressResponse {

    private String uuid;

    private String address;

    private String postalcode;

    private String number;

    private String city;

    private Boolean principal;

}
