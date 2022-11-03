package by.vicky.project_library.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

	public Book findByNameStartingWith(String startingWith) {
		Book book = bookRepository.findByNameStartingWith(startingWith);
		return book;
	}

	public List<Book> findByPersonId(int personId) {
		List<Book> books = bookRepository.findByPersonId(personId);
		List<Book> newBooks = new ArrayList<>();
		for (Book book : books) {

			Long assignedDate = book.getAssignAt().getTime();
			Long moment = new Date().getTime();
			Long delayInDays = (moment - assignedDate) / 86400000;
			if (delayInDays > 10) {
				book.setDelay(true);
			} else {
				book.setDelay(false);
			}
			newBooks.add(book);
		}
		return newBooks;
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
		book.setAssignAt(new Date());
		bookRepository.save(book);
	}

	public List<Book> findAll(Integer page, Integer itemsPerPage) {
		return bookRepository.findAll(PageRequest.of(page, itemsPerPage)).getContent();
	}

	public List<Book> findAll(Integer page, Integer itemsPerPage, boolean sort) {
		if (sort) {
			return bookRepository.findAll(PageRequest.of(page, itemsPerPage, Sort.by("year"))).getContent();
		} else {
			return bookRepository.findAll(PageRequest.of(page, itemsPerPage)).getContent();

		}
	}

}
