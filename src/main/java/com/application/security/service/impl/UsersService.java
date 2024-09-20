package com.application.security.service.impl;

import java.util.List;
import java.util.Optional;

import com.application.security.entity.Users;

public interface UsersService {
	
	Optional<Users> addUser(Users user);

    Optional<Users> getUserById(String id);

    Optional<Users> getUserByName(String name);

    Optional<Users> getUserByEmail(String email);

    Optional<List<Users>> getUserByRole(String role);

    Optional<Users> getUserByNumber(Long number);

   void deleteUserById(String id);  // Added for delete
    
    Users updateUser(Users user); 

}
