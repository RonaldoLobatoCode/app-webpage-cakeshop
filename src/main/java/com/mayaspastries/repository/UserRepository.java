package com.mayaspastries.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mayaspastries.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	User findByUser(String user);
	
	@Query("SELECT u FROM User u WHERE u.user = :user AND u.password = :hashedPassword")
    User findByUsernameAndHashedPassword(@Param("user") String user, @Param("hashedPassword") String hashedPassword);
}
