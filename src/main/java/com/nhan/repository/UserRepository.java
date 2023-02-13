package com.nhan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nhan.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	Optional<User> findByEmail(String slug);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE users u set u.name = :name where u.user_id = :userId", nativeQuery = true)
	int updateName(@Param("userId")int userId, @Param("name") String name);

}
