package com.pessoasapi.repository;

import com.pessoasapi.model.People;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepository extends JpaRepository<People, Integer> {

    People findByUuid(String uuid);

    Page<People> findByNameContaining(String name, Pageable pageable);

}
