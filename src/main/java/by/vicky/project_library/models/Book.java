package by.vicky.project_library.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "book")
public class Book {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotEmpty(message = "Name should not be empty")
	@Column(name = "name")
	private String name;

	@NotEmpty(message = "Author should not be empty")
	@Column(name = "author")
	private String author;

	@NotNull(message = "Year should not be empty")
	@Column(name = "year")
	private int year;

	@Column(name = "person_id")
	private Integer personId;

	@Column(name = "assign_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date assignAt;

	@Transient
	private boolean delay;

	public Book() {
	}

	public Book(String name, String author, int year) {
		this.name = name;
		this.author = author;
		this.year = year;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public Date getAssignAt() {
		return assignAt;
	}

	public void setAssignAt(Date assignAt) {
		this.assignAt = assignAt;
	}

	public boolean isDelay() {
		return delay;
	}

	public void setDelay(boolean delay) {
		this.delay = delay;
	}

}
