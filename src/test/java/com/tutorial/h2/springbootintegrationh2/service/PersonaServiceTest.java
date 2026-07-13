package com.tutorial.h2.springbootintegrationh2.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tutorial.h2.springbootintegrationh2.model.Person;
import com.tutorial.h2.springbootintegrationh2.repository.PersonRepository;

@ExtendWith(MockitoExtension.class)
public class PersonaServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonaService personaService;

    @Test
    public void testGetAllPersons() {
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

        when(personRepository.findAll()).thenReturn(Arrays.asList(person1, person2));

        List<Person> result = personaService.getAllPersons();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Juan", result.get(0).getName());
        assertEquals("Maria", result.get(1).getName());
        verify(personRepository, times(1)).findAll();
    }

    @Test
    public void testGetPersonById_Success() {
        Person person = new Person();
        person.setId(1);
        person.setName("Juan");

        when(personRepository.findById(1)).thenReturn(Optional.of(person));

        Person result = personaService.getPersonById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Juan", result.getName());
        verify(personRepository, times(1)).findById(1);
    }

    @Test
    public void testGetPersonById_NotFound() {
        when(personRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            personaService.getPersonById(1);
        });
        verify(personRepository, times(1)).findById(1);
    }

    @Test
    public void testSaveOrUpdate() {
        Person person = new Person();
        person.setId(1);
        person.setName("Juan");

        personaService.saveOrUpdate(person);

        verify(personRepository, times(1)).save(person);
    }

    @Test
    public void testDelete() {
        personaService.delete(1);

        verify(personRepository, times(1)).deleteById(1);
    }
}
