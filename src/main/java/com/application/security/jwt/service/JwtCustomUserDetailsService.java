package com.application.security.jwt.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.application.security.entity.Users;
import com.application.security.repository.UsersRepository;



@Service
public class JwtCustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UsersRepository usersRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Users> users = usersRepo.findByUserEmail(username);
		if (users == null) {
			throw new UsernameNotFoundException("User Not Found");
		}else {
		return new JwtCustomUserDetails(users);
	}
	}
}