package com.nhan.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nhan.entity.Book;
import com.nhan.exception.EntityExistsException;
import com.nhan.exception.NotFoundException;
import com.nhan.repository.BookRepository;
import com.nhan.service.IBookService;
import com.nhan.utils.Slug;

@Service
public class BookServiceImpl implements IBookService {

	@Autowired
	BookRepository bookRepository;

	public BookServiceImpl(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public <S extends Book> S save(S entity) {
		String slug = Slug.toSlug(entity.getName());
		
		Optional<Book> check = bookRepository.findBySlug(slug);
		if (check.isPresent()) {
			
			if (entity.getBookId() != check.get().getBookId()) {
				throw new EntityExistsException("Tên sách đã tồn tại!");
			} 
		}
		
		entity.setSlug(slug);
		return bookRepository.save(entity);
	}

	@Override
	public List<Book> findAll() {
		return bookRepository.findAll();
	}

	@Override
	public Page<Book> findAll(Pageable pageable) {
		return bookRepository.findAll(pageable);
	}

	@Override
	public List<Book> findAll(Sort sort) {
		return bookRepository.findAll(sort);
	}

	@Override
	public List<Book> findAllById(Iterable<Integer> ids) {
		return bookRepository.findAllById(ids);
	}

	@Override
	public Optional<Book> findById(Integer id) {
		Optional<Book> book = bookRepository.findById(id);
		if (book.isPresent())
			return book;
		throw new NotFoundException("Sách không tồn tại!");
	}
	
	@Override
	public Optional<Book> findBySlug(String slug) {
		Optional<Book> book = bookRepository.findBySlug(slug);
		if (book.isPresent())
			return book;
		throw new NotFoundException("Sách không tồn tại!");
	}

	@Override
	public long count() {
		return bookRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		bookRepository.deleteById(id);
	}
	
	
	
}
