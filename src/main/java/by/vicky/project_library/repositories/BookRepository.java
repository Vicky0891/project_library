package by.vicky.project_library.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import by.vicky.project_library.models.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {

	List<Book> findByNameStartingWith(String startingWith);

	List<Book> findByName(String name);

	List<Book> findByAuthor(String author);

	List<Book> findByPersonId(Integer personId);

}
