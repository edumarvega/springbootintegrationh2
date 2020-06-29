package com.tutorial.h2.springbootintegrationh2.repository;

import org.springframework.data.repository.CrudRepository;

import com.tutorial.h2.springbootintegrationh2.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer>{

}
