package com.nhan.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhan.entity.Category;
import com.nhan.model.CategoryModel;
import com.nhan.model.ResponseObject;
import com.nhan.service.ICategoryService;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {
	
	@Autowired
	ICategoryService categoryService;
	
	@GetMapping("")
	public ResponseEntity<ResponseObject> getAll() {
		List<Category> categories = categoryService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(
				new ResponseObject("Ok", "Ok", categories));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseObject> getById(@PathVariable int id) {
		Optional<Category> category = categoryService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(
				new ResponseObject("Ok", "Ok", category));
	}
	
	@GetMapping("/slug/{slug}")
	public ResponseEntity<ResponseObject> getBySlug(@PathVariable String slug) {
		Optional<Category> category = categoryService.findBySlug(slug);
		return ResponseEntity.status(HttpStatus.OK).body(
				new ResponseObject("Ok", "Ok", category));
	}
	
	@PostMapping("")
	public ResponseEntity<?> create(@RequestBody CategoryModel model) {
		Category entity = new Category();
		BeanUtils.copyProperties(model, entity);
		Category result = categoryService.save(entity);
		return ResponseEntity.status(HttpStatus.OK).body(
				new ResponseObject("Ok", "Thêm mới thành công", result));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ResponseObject> update(@PathVariable int id, @RequestBody CategoryModel model) {
		Optional<Category> category = categoryService.findById(id);
		Category cate = category.get();
		cate.setName(model.getName());
		return ResponseEntity.status(HttpStatus.OK).body(
				new ResponseObject("Ok", "Cập nhật thành công", categoryService.updateNameAndSlug(cate))); 
			
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseObject> deleteById(@PathVariable int id) {
		categoryService.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).body(
				new ResponseObject("Ok", "Ok", null));
	}
	
}
