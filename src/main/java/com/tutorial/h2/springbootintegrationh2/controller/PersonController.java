package com.tutorial.h2.springbootintegrationh2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tutorial.h2.springbootintegrationh2.model.Person;
import com.tutorial.h2.springbootintegrationh2.service.PersonaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Persons microservice", description = "API REST CRUD de personas")
public class PersonController {
	
	@Autowired
	PersonaService personService;
	
	

	@GetMapping("/persons")
	@ApiOperation(value = "getAllPersons", notes = "Devuelve todas las personas" )
	private List<Person> getAllPersons(){
		return personService.getAllPersons();
	}
	
	@GetMapping("/persons/{id}")
	@ApiOperation(value = "getPerson", notes = "Devuelve una persona por id" )
	private Person getPerson(@PathVariable("id") int id) {
		return personService.getPersonById(id);
	}
	
	@DeleteMapping("/persons/{id}")
	@ApiOperation(value = "deletePerson", notes = "Devuelve todas las personas" )
	private void deletePerson(@PathVariable("id") int id) {
		personService.delete(id);
	}
	
	@PostMapping("/persons")
	@ApiOperation(value = "saveOrUpdatePerson", notes = "Crea o actualiza los datos de una persona" )
	private int saveOrUpdatePerson(@RequestBody Person person) {
		//hay que desblar este endopoint en dos
		// uno por post para grabar una persona
		// otro put para actualizar los datos de la persona
		personService.saveOrUpdate(person);
		return person.getId();
	}
	
}
