package br.com.gabriel.meuslivros.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gabriel.meuslivros.models.Book;
import br.com.gabriel.meuslivros.repository.BookRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api")
@Api(value = "API REST Meus Livros")
@CrossOrigin(origins = "*")
public class BookController {

	@Autowired
	BookRepository bookRepository;

	BookController(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@GetMapping("/my-books")
	@ApiOperation(value = "Retorna todos os livros cadastrados")
	public List<Book> list() {
		return bookRepository.findAll();
	}

	@GetMapping("/my-books/{id}")
	@ApiOperation(value = "Retorna um determinado livro")
	public Book listById(@PathVariable(value = "id") long id) {
		return bookRepository.findById(id);
	}
	
	@GetMapping("/my-books/category")
	@ApiOperation(value = "Retorna um determinado livro")
	public ResponseEntity<?> listByCategory(@RequestParam(value = "category") String category) {
		
		Book book = new Book();
		String[] tempParams = category.split(",");
		List<String> params = new ArrayList<>();
		
		for (String string : tempParams) {
			params.add(string);
		}
				
		if (!book.validateCategory(params)) {
			return new ResponseEntity<>(new IllegalArgumentException("Categoria Inválida"), HttpStatus.BAD_REQUEST);
		}
		
		//Não consegui fazer a query com mais de um parametro
		return new ResponseEntity<>(bookRepository.findByCategories(category), HttpStatus.OK);
	}

	@PostMapping("/my-books")
	@ApiOperation(value = "Cadastra um novo livro")
	public ResponseEntity<?> create(@RequestBody Book book) {
		if (!book.validateCategory(book.getCategories())) {
			return new ResponseEntity<>(new IllegalArgumentException("Categoria Inválida"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(bookRepository.save(book), HttpStatus.OK);
	}

	@DeleteMapping("/my-books/{id}")
	@ApiOperation(value = "Deleta um livro especifico")
	public void delete(@PathVariable(value="id") long id) {
		bookRepository.deleteById(id);
	}

	@PutMapping("/my-books/{id}")
	@ApiOperation(value = "Atualiza um livro especifico")
	public ResponseEntity<?> update(@PathVariable(value="id") long id, @RequestBody Book book) {
		
		Book temp = bookRepository.findById(id);
		temp.setCategories(book.getCategories());
		temp.setName(book.getName());
		temp.setValue(book.getValue());
		
		if (!book.validateCategory(book.getCategories())) {
			return new ResponseEntity<>(new IllegalArgumentException("Categoria Inválida"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(bookRepository.save(temp), HttpStatus.OK);
		
	}

}
