package by.vicky.project_library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import by.vicky.project_library.models.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {
	Person findByFullName(String fullName);

}
