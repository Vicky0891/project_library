package by.vicky.project_library.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import by.vicky.project_library.models.Book;
import by.vicky.project_library.models.Person;
import by.vicky.project_library.services.BookService;
import by.vicky.project_library.services.PersonService;
import by.vicky.project_library.utils.BookValidator;

@Controller
@RequestMapping("/books")
public class BookController {

	private final PersonService personService;
	private final BookService bookService;
	private final BookValidator bookValidator;

	@Autowired
	public BookController(BookService bookService, PersonService personService, BookValidator bookValidator) {
		this.bookService = bookService;
		this.personService = personService;
		this.bookValidator = bookValidator;
	}

	@GetMapping()
	public String index(Model model) {
		model.addAttribute("books", bookService.findAll());
		return "books/index";
	}

	@GetMapping("/new")
	public String newBook(@ModelAttribute("book") Book book) {
		return "books/new";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
		model.addAttribute("book", bookService.findOne(id));
		Book book = bookService.findOne(id);
		if (book.getPersonId() == null) {
			model.addAttribute("people", personService.findAll());
		} else {
			model.addAttribute("owner", personService.findOne(book.getPersonId()));
		}
		return "books/show";
	}

	@PostMapping
	public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
		bookValidator.validate(book, bindingResult);
		if (bindingResult.hasErrors()) {
			return "books/new";
		}
		bookService.save(book);
		return "redirect:/books";
	}

	@GetMapping("/{id}/edit")
	public String edit(Model model, @PathVariable("id") int id) {
		model.addAttribute("book", bookService.findOne(id));
		return "books/edit";
	}

	@PatchMapping("/{id}")
	public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
			@PathVariable("id") int id) {
		bookValidator.validate(book, bindingResult);
		if (bindingResult.hasErrors()) {
			return "books/edit";
		}
		bookService.update(id, book);
		return "redirect:/books";
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") int id) {
		bookService.delete(id);
		return "redirect:/books";
	}

	@PatchMapping("/{id}/free")
	public String free(@PathVariable("id") int id) {
		bookService.release(id);
		return "redirect:/books/" + id;
	}

	@PatchMapping("/{id}/assign")
	public String assign(@ModelAttribute("person") Person person, @PathVariable("id") int id) {
		bookService.assign(id, person);
		return "redirect:/books/" + id;
	}

	@GetMapping("/search")
	public String searchForm() {
		return "books/search";
	}

	@PostMapping("/search")
	public String searchBook(@RequestParam String name, Model model) {
		Book book = bookService.findByNameStartingWith(name);
		if (book != null) {
			model.addAttribute("book", book);
			if (book.getPersonId() != null) {
				model.addAttribute("owner", personService.findOne(book.getPersonId()));
			}
		} else {
			model.addAttribute("error", "Error");
		}
		return "books/search";
	}

}
