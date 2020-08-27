package br.com.gabriel.meuslivros.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String name;
	private BigDecimal value;

	@ElementCollection(targetClass = String.class)
	private List<String> categories;

	public Book(long id, String name, BigDecimal value, List<String> categories) {
		this.id = id;
		this.name = name;
		this.value = value;
		this.categories = categories;
	}
	
	public Book() {
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public Boolean validateCategory(List<String> categories) {

		try {
			categories.forEach(category -> {
				CategoriesEnum.valueOf(category);
			});
		} catch (IllegalArgumentException e) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", value=" + value + ", categories=" + categories + "]";
	}

}

enum CategoriesEnum {
	COMEDIA, ROMANCE, TERROR, ANIMACAO
}
