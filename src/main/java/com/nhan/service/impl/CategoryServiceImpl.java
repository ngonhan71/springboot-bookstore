package com.nhan.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nhan.entity.Category;
import com.nhan.exception.EntityExistsException;
import com.nhan.exception.NotFoundException;
import com.nhan.repository.CategoryRepository;
import com.nhan.service.ICategoryService;
import com.nhan.utils.Slug;

@Service
public class CategoryServiceImpl implements ICategoryService {
	
	@Autowired
	CategoryRepository categoryRepository;

	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public <S extends Category> S save(S entity) {
		String slug = Slug.toSlug(entity.getName());
		
		Optional<Category> check = categoryRepository.findBySlug(slug);
		
		if (check.isPresent()) {
			throw new EntityExistsException("Danh mục đã tồn tại!");
		}
		
		entity.setSlug(slug);
		return categoryRepository.save(entity);
	}

	@Override
	public int updateNameAndSlug(Category entity) {
		String slug = Slug.toSlug(entity.getName());
		Optional<Category> check = categoryRepository.findBySlug(slug);
		if (check.isPresent() && check.get().getCategoryId() != entity.getCategoryId()) 
			throw new EntityExistsException("Danh mục đã tồn tại!");
		
		if (categoryRepository.updateNameAndSlug(entity.getCategoryId(), entity.getName(), slug) == 1) {
			return 1;
		}
		throw new NotFoundException("Danh mục không tồn tại!");
	}
	
	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	@Override
	public Optional<Category> findById(Integer id) {
		Optional<Category> category = categoryRepository.findById(id);
		if (category.isPresent())
			return category;
		throw new NotFoundException("Danh mục không tồn tại!");
	}
	
	@Override
	public Optional<Category> findBySlug(String slug) {
		Optional<Category> category = categoryRepository.findBySlug(slug);
		if (category.isPresent())
			return category;
		throw new NotFoundException("Danh mục không tồn tại!");
	}

	@Override
	public Page<Category> findAll(Pageable pageable) {
		return categoryRepository.findAll(pageable);
	}

	@Override
	public List<Category> findAll(Sort sort) {
		return categoryRepository.findAll(sort);
	}

	@Override
	public List<Category> findAllById(Iterable<Integer> ids) {
		return categoryRepository.findAllById(ids);
	}

	@Override
	public long count() {
		return categoryRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		categoryRepository.deleteById(id);
	}

	
}
