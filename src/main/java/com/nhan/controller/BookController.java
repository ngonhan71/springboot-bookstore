package com.nhan.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhan.entity.Book;
import com.nhan.entity.Category;
import com.nhan.model.BookModel;
import com.nhan.model.ResponseObject;
import com.nhan.service.IBookService;

@RestController
@RequestMapping("api/v1/books")
public class BookController {
	
	@Autowired
	IBookService bookService;
	
	
	@GetMapping("")
	public ResponseEntity<ResponseObject> getAll() {
		
		List<Book> books = bookService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(
				new ResponseObject("Ok", "Ok", books));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseObject> getById(@PathVariable int id) {
		Optional<Book> book = bookService.findById(id);
		if (book.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(
					new ResponseObject("Ok", "Ok", book));
		} else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new ResponseObject("Error", "Không tìm thấy sách", null));
	}
	
	@GetMapping("/slug/{slug}")
	public ResponseEntity<ResponseObject> getBySlug(@PathVariable String slug) {
		Optional<Book> book = bookService.findBySlug(slug);
		return ResponseEntity.status(HttpStatus.OK).body(
				new ResponseObject("Ok", "Ok", book));
	}
	
	@PostMapping("")
	public ResponseEntity<?> create(@RequestBody BookModel model) {
		Book entity = new Book();
		BeanUtils.copyProperties(model, entity);
		
		Category cateEntity = new Category();
		cateEntity.setCategoryId(model.getCategoryId());
		
		entity.setCategory(cateEntity);
		
		Book result = bookService.save(entity);
		return ResponseEntity.status(HttpStatus.OK).body(
				new ResponseObject("Ok", "Thêm mới thành công", result));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ResponseObject> update(@PathVariable int id, @RequestBody BookModel model) {
		bookService.findById(id);
		Book entity = new Book();
		BeanUtils.copyProperties(model, entity);
		
		Category cateEntity = new Category();
		cateEntity.setCategoryId(model.getCategoryId());
		
		entity.setBookId(id);
		entity.setCategory(cateEntity);

		return ResponseEntity.status(HttpStatus.OK).body(
				new ResponseObject("Ok", "Cập nhật thành công", bookService.save(entity))); 
	}
}
