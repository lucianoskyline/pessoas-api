package com.pessoasapi.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "people_address")
@Data
public class PeopleAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "person", referencedColumnName = "id")
    private People person;

    private String address;

    private String postalcode;

    private String number;

    private String city;

    private Boolean principal;

    private String uuid;

}
