package com.nhan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nhan.entity.CartId;
import com.nhan.entity.CartItem;

@Repository
public interface CartRepository extends JpaRepository<CartItem, CartId> {

	@Query(value = "SELECT * FROM cart_items ci where ci.user_id = :userId", nativeQuery = true)
	List<CartItem> findByUserId(int userId);
	
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO cart_items VALUES(:bookId, :userId, :quantity)", nativeQuery = true)
	int addToCart(@Param("userId") int userId, @Param("bookId")int bookId, @Param("quantity") int quantity);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE cart_items ci set ci.quantity = :quantity where ci.user_id = :userId and ci.book_id = :bookId", nativeQuery = true)
	int updateQuantity(@Param("userId") int userId, @Param("bookId")int bookId, @Param("quantity") int quantity);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM cart_items where user_id = :userId and book_id = :bookId", nativeQuery = true)
	int removeItem(@Param("userId") int userId, @Param("bookId")int bookId);
}
