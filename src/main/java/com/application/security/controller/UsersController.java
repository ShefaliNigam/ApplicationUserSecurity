package com.application.security.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.security.entity.StockEntity;
import com.application.security.entity.Users;
import com.application.security.exception.UserAlreadyExistsException;
import com.application.security.repository.UsersRepository;
import com.application.security.service.impl.UsersServiceImpl;


@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UsersController {

	@Autowired
	private UsersServiceImpl usersServiceImpl;
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@PostMapping("/addNewUser")
	public ResponseEntity<Users> createUser(@RequestBody Users user){
		
		if(usersRepository.findByUserEmail(user.getUserEmail()).isPresent()) {
			throw new UserAlreadyExistsException("User exists");
		}
		user.setUserPassword(bCryptPasswordEncoder.encode(user.getUserPassword()));
		user.setUserRole("USER");
		 Users users =  usersServiceImpl.addUser(user).get();
		return new ResponseEntity<Users>(users,HttpStatus.CREATED);
	}
	
	 @PostMapping("/{userId}/addStock")
	    public ResponseEntity<Users> addStockToUser(@PathVariable("userId") Integer userId,
	                                                @RequestBody StockEntity stockEntity) {
	        Optional<Users> updatedUser = usersServiceImpl.addStockToUser(userId, stockEntity);
	        
	        if (updatedUser.isPresent()) {
	            return new ResponseEntity<>(updatedUser.get(), HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Handle user not found case
	        }
	 }
	
	@GetMapping("/id/{id}")
	public ResponseEntity<Users> getUserById(@PathVariable("id") String id){
		System.out.println("id : "+ id);
		Users user = usersServiceImpl.getUserById(id).get();
		return new ResponseEntity<Users>(user,HttpStatus.ACCEPTED);
	}
	@GetMapping("/getAllUsers")
	public ResponseEntity<List<Users>> getAllData(){
		List<Users> listUsers = usersServiceImpl.getAllData();
		return new ResponseEntity<List<Users>>(listUsers,HttpStatus.OK);
	}
	
//	private String nameHCD = "sadan";
	@GetMapping("/name/{name}")
	public ResponseEntity<Users> getUserByName(@PathVariable("name") String name){
		Users userData = usersServiceImpl.getUserByName(name).get();
		return new ResponseEntity<Users>(userData,HttpStatus.OK);
	}
	
	@GetMapping("/email")
	public ResponseEntity<Users> getUserByEmail(@RequestParam("email") String email){
		Users u = usersServiceImpl.getUserByEmail(email).get();
		return new ResponseEntity<Users>(u,HttpStatus.OK);
	}
	@GetMapping("/role")
	public ResponseEntity<List<Users>>  getUsersByRole(@RequestParam("role") String role){
		List<Users> users = usersServiceImpl.getUserByRole(role).get();
		return new ResponseEntity<List<Users>>(users,HttpStatus.OK);
	}
	
	@GetMapping("/mobile")
	public ResponseEntity<Users> getUserByNumber(@RequestParam("mobile") Long number){
		Users u = usersServiceImpl.getUserByNumber(number).get();
		return new ResponseEntity<Users>(u,HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteUserById(@PathVariable("id") String id) {
	    if (usersServiceImpl.getUserById(id).isPresent()) {
	        usersServiceImpl.deleteUserById(id);
	        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
	    }
	}
	
	
	@PostMapping("/{userId}/addStockInExistingUser")
	public ResponseEntity<Users> addExistingUser(@PathVariable("userId") String userId, @RequestBody StockEntity stock) {
	    Users user = usersServiceImpl.getUserById(userId).orElseThrow(() -> new RuntimeException("User not found"));

	   
	    user.getStocks().add(stock);

	    
	    Users updatedUser = usersServiceImpl.addUser(user).get();

	    return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
	}
	@PutMapping("/updateUser/{id}")
	public ResponseEntity<Users> updateUserById(@PathVariable("id") String id, @RequestBody Users updatedUser) {
	    Optional<Users> existingUserOpt = usersServiceImpl.getUserById(id);
	    
	    if (existingUserOpt.isPresent()) {
	        Users existingUser = existingUserOpt.get();
	        existingUser.setUserName(updatedUser.getUserName());
	        existingUser.setUserEmail(updatedUser.getUserEmail());
	        existingUser.setUserMobile(updatedUser.getUserMobile());
	        existingUser.setUserPassword(bCryptPasswordEncoder.encode(updatedUser.getUserPassword()));  // Update password
	        existingUser.setUserRole(updatedUser.getUserRole());
	        
	        Users savedUser = usersServiceImpl.updateUser(existingUser);
	        return new ResponseEntity<>(savedUser, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}

	/*
	 * @GetMapping("/home") public String home() { return "This is Home Page"; }
	 */
}
