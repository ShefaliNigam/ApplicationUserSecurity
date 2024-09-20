package com.application.security.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.application.security.entity.StockEntity;
import com.application.security.entity.Users;
import com.application.security.repository.UsersRepository;

@Service
public class UsersServiceImpl implements UsersService {
	
	@Autowired
	private UsersRepository usersRepository;
	
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private RestTemplate restTemplate;

    private final String STOCK_API_BASE_URL = "http://localhost:9093/api/stocks";

    public List<StockEntity> getUserStocks(String userId) {
        // Call the stock service using RestTemplate to fetch stock data
        String url = STOCK_API_BASE_URL + "/add/" + userId;

        StockEntity[] stocks = restTemplate.getForObject(url, StockEntity[].class);

        // Convert array to List
        return Arrays.asList(stocks);
    }

    public StockEntity getStockById(Long stockId) {
        // Call the stock service to get a specific stock by ID
        String url = STOCK_API_BASE_URL + "/" + stockId;

        return restTemplate.getForObject(url, StockEntity.class);
    }

    public List<StockEntity> addStock(List<StockEntity> stockEntity) {
        // Call the stock service to create a new stock
        String url = STOCK_API_BASE_URL + "/add";

        StockEntity[] stocks = restTemplate.postForObject(url, stockEntity, StockEntity[].class);
        return Arrays.asList(stocks);
    }
    
    public Optional<Users> addStockToUser(Integer userId, StockEntity stockEntity) {
        Optional<List<Users>> userOptional = usersRepository.findByUserId(userId);

        if (userOptional.isPresent()) {
            List<Users> user = userOptional.get();

            // Add the stock to the user's stock list
            stockEntity.setUserId(userId);
            ((Users) user).getStocks().add(stockEntity);

            // Save updated user
            usersRepository.save(user);
            return Optional.empty();
        } else {
            return Optional.empty();  // Handle user not found case
        }
    }
    
	public Optional<Users> addUser(Users users) {
		//Integer uid = generateUniqueUid();
		//users.setUserId(uid);
		List<StockEntity> stocks = this.addStock(users.getStocks());
		users.setStocks(stocks);
		Users user = usersRepository.save(users);
		return Optional.of(user);
	}
	
	public Optional<Users> getUserById(String id){
		Optional<Users> user = usersRepository.findById(id);
		return user;
	}
	
	public Optional<Users> getUserByName(String name){
		Optional<Users> user =usersRepository.findByUserName(name);
		return user;
	}
	
	public Optional<Users> getUserByEmail(String email){
		Optional<Users> user = usersRepository.findByUserEmail(email);
		return user;
	}
	
	public Optional<Users> getUserByNumber(Long number){
		Optional<Users> user = usersRepository.findByUserMobile(number);
		return user;
	}
	
	public Optional<List<Users>> getUserByRole(String role){
		Optional<List<Users>> users = usersRepository.findByUserRole(role);
		return users;
	}
	
    private Integer generateUniqueUid() {
        Long sequenceValue = jdbcTemplate.queryForObject("SELECT nextval('user_uid_seq')", Long.class);
        return sequenceValue.intValue();
    }

	public List<Users> getAllData() {
		// TODO Auto-generated method stub
		return usersRepository.findAll();
	}

	    public void deleteUserById(String id) {
	        if (usersRepository.existsById(id)) {
	            usersRepository.deleteById(id);
	        } else {
	            throw new RuntimeException("User not found with ID: " + id);
	        }
	    }

	   public Users updateUser(Users user) {
	        return usersRepository.save(user);  // Save will update if the user exists
	    }

	    // Method to generate a unique user ID, if needed
	    private Integer generateUniqueUid1() {
	        Long sequenceValue = jdbcTemplate.queryForObject("SELECT nextval('user_uid_seq')", Long.class);
	        return sequenceValue.intValue();
	    }

	    
	    
	    public Optional<Users> addExistingUser(Users user) {
	        return Optional.of(usersRepository.save(user));
	    }
		
	



}
