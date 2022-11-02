package by.vicky.project_library.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import by.vicky.project_library.models.Book;
import by.vicky.project_library.repositories.BookRepository;

@Component
public class BookValidator implements Validator {

	private final BookRepository bookRepository;

	@Autowired
	public BookValidator(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Book.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Book book = (Book) target;
		if (bookRepository.findByName(book.getName()) != null
				&& (bookRepository.findByAuthor(book.getAuthor()) != null)) {
			errors.rejectValue("Name", "", "Book with same name and author already exists");
		}
	}

}
