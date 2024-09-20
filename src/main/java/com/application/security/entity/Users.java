package com.application.security.entity;

import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "lottery_users")
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID) 
	private String id;
	@Column(nullable = false)
	private Integer userId;
	private String userName;
	private String userEmail;
	private String userPassword;
	private Long userMobile;
	private String userRole;
	//@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@JdbcTypeCode(SqlTypes.JSON)
	private List<StockEntity> stocks;
	
	public Users() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Long getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(Long userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	
	 public List<StockEntity> getStocks() {
	        return stocks;
	    }

	    public void setStocks(List<StockEntity> stocks) {
	        this.stocks = stocks;
	    }

	public Users(String id, Integer userId, String userName, String userEmail, String userPassword, Long userMobile,
			String userRole,List<StockEntity> stocks) {
		super();
		this.id = id;
		this.userId = userId;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.userMobile = userMobile;
		this.userRole = userRole;
		this.stocks = stocks;
	}

	@Override
	public String toString() {
		return "Users [id=" + id + ", userId=" + userId + ", userName=" + userName + ", userEmail=" + userEmail
				+ ", userPassword=" + userPassword + ", userMobile=" + userMobile + ", userRole=" + userRole
				+ ", stocks=" + stocks + "]";
	}

}
	
	
	
	
    


