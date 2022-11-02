package by.vicky.project_library.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.vicky.project_library.models.Person;
import by.vicky.project_library.repositories.PersonRepository;

@Service
@Transactional(readOnly = true)
public class PersonService {

	private final PersonRepository personRepository;

	@Autowired
	public PersonService(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	public List<Person> findAll() {
		return personRepository.findAll();
	}

	public Person findOne(int id) {
		Optional<Person> foundPerson = personRepository.findById(id);
		return foundPerson.orElse(null);
	}

	public Person findByFullName(String fullName) {
		Person foundPerson = personRepository.findByFullName(fullName);
		return foundPerson;
	}

	@Transactional
	public void save(Person person) {
		personRepository.save(person);
	}

	@Transactional
	public void update(int id, Person updatedPerson) {
		updatedPerson.setId(id);
		personRepository.save(updatedPerson);
	}

	@Transactional
	public void delete(int id) {
		personRepository.deleteById(id);
	}

}
