package by.vicky.project_library.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.vicky.project_library.models.Book;
import by.vicky.project_library.models.Person;
import by.vicky.project_library.repositories.BookRepository;

@Service
@Transactional(readOnly = true)
public class BookService {

	private final BookRepository bookRepository;

	@Autowired
	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public List<Book> findAll() {
		return bookRepository.findAll();
	}

	public Book findOne(int id) {
		Optional<Book> foundBook = bookRepository.findById(id);
		return foundBook.orElse(null);
	}

	@Transactional
	public void save(Book book) {
		bookRepository.save(book);
	}

	@Transactional
	public void update(int id, Book updatedBook) {
		updatedBook.setId(id);
		bookRepository.save(updatedBook);
	}

	@Transactional
	public void delete(int id) {
		bookRepository.deleteById(id);
	}

	public List<Book> findByNameStartingWith(String startingWith) {
		return bookRepository.findByNameStartingWith(startingWith);
	}

	public List<Book> findByPersonId(int personId) {
		return bookRepository.findByPersonId(personId);
	}

	@Transactional
	public void release(int id) {
		Book book = bookRepository.findById(id).orElse(null);
		book.setPersonId(null);
		bookRepository.save(book);
	}

	@Transactional
	public void assign(int id, Person assignedPerson) {
		Book book = bookRepository.findById(id).orElse(null);
		book.setPersonId(assignedPerson.getId());
		bookRepository.save(book);
	}

}
