package com.nhan.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.nhan.entity.Category;

public interface ICategoryService {

	void deleteById(Integer id);

	long count();

	List<Category> findAllById(Iterable<Integer> ids);

	List<Category> findAll(Sort sort);

	Page<Category> findAll(Pageable pageable);

	List<Category> findAll();

	<S extends Category> S save(S entity);

	Optional<Category> findById(Integer id);
	
	Optional<Category> findBySlug(String slug);

	int updateNameAndSlug(Category entity);
}
