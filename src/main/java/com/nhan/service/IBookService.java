package com.nhan.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.nhan.entity.Book;

public interface IBookService {

	void deleteById(Integer id);

	long count();

	Optional<Book> findById(Integer id);

	Optional<Book> findBySlug(String slug);

	List<Book> findAllById(Iterable<Integer> ids);

	List<Book> findAll(Sort sort);

	Page<Book> findAll(Pageable pageable);

	List<Book> findAll();

	<S extends Book> S save(S entity);

}
