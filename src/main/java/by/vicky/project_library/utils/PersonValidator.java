package by.vicky.project_library.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import by.vicky.project_library.models.Person;
import by.vicky.project_library.services.PersonService;

@Component
public class PersonValidator implements Validator {

	private final PersonService personService;

	@Autowired
	public PersonValidator(PersonService personService) {
		this.personService = personService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Person.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Person person = (Person) target;
		if (personService.findByFullName(person.getFullName()) != null) {
			errors.rejectValue("fullName", "", "Person with same name already exists");
		}
	}

}
