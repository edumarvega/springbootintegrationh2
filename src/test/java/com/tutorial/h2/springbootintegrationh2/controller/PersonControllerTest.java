package com.tutorial.h2.springbootintegrationh2.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutorial.h2.springbootintegrationh2.model.Person;
import com.tutorial.h2.springbootintegrationh2.service.PersonaService;

@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonaService personaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllPersons() throws Exception {
        Person person1 = new Person();
        person1.setId(1);
        person1.setName("Juan");
        person1.setAge(30);
        person1.setEmailId("juan@example.com");

        Person person2 = new Person();
        person2.setId(2);
        person2.setName("Maria");
        person2.setAge(25);
        person2.setEmailId("maria@example.com");

        when(personaService.getAllPersons()).thenReturn(Arrays.asList(person1, person2));

        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Juan"))
                .andExpect(jsonPath("$[0].age").value(30))
                .andExpect(jsonPath("$[0].emailId").value("juan@example.com"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Maria"))
                .andExpect(jsonPath("$[1].age").value(25))
                .andExpect(jsonPath("$[1].emailId").value("maria@example.com"));

        verify(personaService, times(1)).getAllPersons();
    }

    @Test
    public void testGetPersonById() throws Exception {
        Person person = new Person();
        person.setId(1);
        person.setName("Juan");
        person.setAge(30);
        person.setEmailId("juan@example.com");

        when(personaService.getPersonById(1)).thenReturn(person);

        mockMvc.perform(get("/persons/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Juan"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.emailId").value("juan@example.com"));

        verify(personaService, times(1)).getPersonById(1);
    }

    @Test
    public void testDeletePerson() throws Exception {
        doNothing().when(personaService).delete(1);

        mockMvc.perform(delete("/persons/1"))
                .andExpect(status().isOk());

        verify(personaService, times(1)).delete(1);
    }

    @Test
    public void testSaveOrUpdatePerson() throws Exception {
        Person person = new Person();
        person.setId(1);
        person.setName("Juan");
        person.setAge(30);
        person.setEmailId("juan@example.com");

        doNothing().when(personaService).saveOrUpdate(any(Person.class));

        mockMvc.perform(post("/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        verify(personaService, times(1)).saveOrUpdate(any(Person.class));
    }
}
