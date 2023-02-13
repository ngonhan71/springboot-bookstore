package com.nhan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nhan.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	Optional<Category> findBySlug(String slug);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE categories cate set cate.name = :name, cate.slug = :slug where cate.category_id = :categoryId", nativeQuery = true)
	int updateNameAndSlug(@Param("categoryId")int categoryId, @Param("name") String name, @Param("slug") String slug);
}
