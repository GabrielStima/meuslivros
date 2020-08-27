package br.com.gabriel.meuslivros.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gabriel.meuslivros.models.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	Book findById(long id);
	
	List<Book> findByCategories(String categories);
}
