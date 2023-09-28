package com.mosin.myTestSpringMVC1.util;

import com.mosin.myTestSpringMVC1.dao.PersonDAO;
import com.mosin.myTestSpringMVC1.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {

    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Person person = (Person) target;
        Optional<Person> result = personDAO.show(person.getName());
        if (result.isPresent() && person.getId() != result.get().getId()) {
            errors.rejectValue("name", "", "This person already exist");
        }

    }
}
