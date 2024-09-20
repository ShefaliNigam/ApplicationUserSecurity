package com.application.security.jwt.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.application.security.exception.NoDetailsException;
import com.application.security.jwt.Entity.JwtRequest;
import com.application.security.jwt.Entity.JwtResponse;
import com.application.security.jwt.helper.JwtUtil;
import com.application.security.jwt.service.JwtCustomUserDetailsService;

@RestController
@CrossOrigin(origins = "*")
public class JwtController {



	@Autowired
	private AuthenticationManager authenticationManager;

	private JwtResponse jwtResponse;

	@Autowired
	private JwtCustomUserDetailsService jwtCustomUserDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/token")
	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {

		try {

			this.authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(jwtRequest.getUserEmail(), jwtRequest.getUserPassword()));

		} catch (BadCredentialsException e) {
//            e.printStackTrace();
			throw new BadCredentialsException("Bad Credentials");
		} catch (NoDetailsException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No Details Found");
//			throw new NoDetailsException("Wrong Credentials");
		} catch (UsernameNotFoundException e) {
	        throw new BadCredentialsException("Username and password are not valid");
	    }

		// fine area..
		try {
			UserDetails userDetails = this.jwtCustomUserDetailsService.loadUserByUsername(jwtRequest.getUserEmail());
			String token = this.jwtUtil.generateToken(userDetails);

			// {"token":"value"
			Collection<? extends GrantedAuthority> role = userDetails.getAuthorities();
////	        role.toString();
//	        role.toArray();


//			return ResponseEntity.ok(new JwtResponse(token + " " + role));
//			return ResponseEntity.ok(new JwtResponse(token));
			return ResponseEntity.ok(token);
		}catch (UsernameNotFoundException e) {
	        // Handle the case where the username is not found
	        throw new UsernameNotFoundException("Username and password are not valid");
	    }

		
		
	}

}