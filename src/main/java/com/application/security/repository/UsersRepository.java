package com.application.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.security.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, String>{

	public Optional<Users> findByUserName(String name);
	public Optional<Users> findByUserEmail(String email);
	public Optional<Users> findByUserMobile(Long number);
	public Optional<List<Users>> findByUserRole (String role);
	
	public Optional<List<Users>> findByUserId(Integer userId);
	public void save(List<Users> user);
	
}
