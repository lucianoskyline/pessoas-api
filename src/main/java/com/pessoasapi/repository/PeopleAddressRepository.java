package com.pessoasapi.repository;

import com.pessoasapi.model.People;
import com.pessoasapi.model.PeopleAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeopleAddressRepository extends JpaRepository<PeopleAddress, Integer> {

    List<PeopleAddress> findByPerson(People person);

}
