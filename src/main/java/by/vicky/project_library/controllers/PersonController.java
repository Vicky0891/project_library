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

import by.vicky.project_library.models.Person;
import by.vicky.project_library.services.BookService;
import by.vicky.project_library.services.PersonService;
import by.vicky.project_library.utils.PersonValidator;

@Controller
@RequestMapping("/people")
public class PersonController {

	private final PersonService personService;
	private final BookService bookService;
	private final PersonValidator personValidator;

	@Autowired
	public PersonController(PersonService personService, BookService bookService, PersonValidator personValidator) {
		this.personService = personService;
		this.bookService = bookService;
		this.personValidator = personValidator;
	}

	@GetMapping()
	public String index(Model model) {
		model.addAttribute("people", personService.findAll());
		return "people/index";
	}

	@GetMapping("/new")
	public String newPerson(@ModelAttribute("person") Person person) {
		return "people/new";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable("id") int personId, Model model) {
		model.addAttribute("person", personService.findOne(personId));
		model.addAttribute("books", bookService.findByPersonId(personId));
		return "people/show";
	}

	@PostMapping
	public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
		personValidator.validate(person, bindingResult);
		if (bindingResult.hasErrors()) {
			return "people/new";
		}
		personService.save(person);
		return "redirect:/people";
	}

	@GetMapping("/{id}/edit")
	public String edit(Model model, @PathVariable("id") int id) {
		model.addAttribute("person", personService.findOne(id));
		return "people/edit";
	}

	@PatchMapping("/{id}")
	public String update(@ModelAttribute("person") @Valid Person person, @PathVariable("id") int id) {
		personService.update(id, person);
		return "redirect:/people";
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") int id) {
		personService.delete(id);
		return "redirect:/people";
	}

}
