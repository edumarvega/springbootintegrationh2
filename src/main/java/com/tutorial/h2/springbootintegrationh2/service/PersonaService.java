package com.tutorial.h2.springbootintegrationh2.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tutorial.h2.springbootintegrationh2.model.Person;
import com.tutorial.h2.springbootintegrationh2.repository.PersonRepository;

@Service
public class PersonaService {

	@Autowired
	private PersonRepository personRepository;
	
	
	
	public List<Person> getAllPersons(){
		
		List<Person> persons = new ArrayList<>();
		
		personRepository.findAll().forEach(person->persons.add(person));
		
		return persons;
		
	}
	
	public Person getPersonById(int id) {
		
		return personRepository.findById(id).get();
	}
	
	public void saveOrUpdate(Person person) {
		
		personRepository.save(person);
	}

	public void delete(int id) {
		
		personRepository.deleteById(id);
	}
}
