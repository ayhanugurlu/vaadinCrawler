package com.au.example.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.au.example.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findByLastNameStartsWithIgnoreCase(String lastName);

	List<User> findByEmailStartsWithIgnoreCase(String email);
}