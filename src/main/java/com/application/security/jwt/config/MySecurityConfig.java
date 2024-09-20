package com.application.security.jwt.config;//

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.application.security.jwt.service.JwtCustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class MySecurityConfig {
	
	@Autowired
	JwtCustomUserDetailsService customerDetailsService;
	
	  @Autowired
	    private JwtAuthenticationFilter jwtFilter;

	    @Autowired
	    private JwtAuthenticationEntryPoint entryPoint;
	
	   
	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    	
	    	http
			.csrf().disable()
//			.cors().disable()
			.authorizeRequests()
			
			.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
			
			.requestMatchers("/user/addNewUser").permitAll()
			.requestMatchers("/user/getAllUsers").permitAll()
			.requestMatchers("user/id/{id}").permitAll()
			.requestMatchers("/user/name/{name}").permitAll()
			.requestMatchers("/user/email").permitAll()
			.requestMatchers("/user/role").permitAll()
			.requestMatchers("/user/mobile").permitAll()
			.requestMatchers("/token").permitAll()
			.requestMatchers("/app/login").permitAll()
			.requestMatchers(HttpMethod.OPTIONS).permitAll()
			.requestMatchers("/user/updateUser/{id}").permitAll()
			.requestMatchers("/user/delete/{id}").permitAll()
			.requestMatchers("/user/{userId}/addStockInExistingUser").permitAll()
			
			

			.requestMatchers("/user/**").authenticated()
			
			.anyRequest()
			.authenticated()
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling().authenticationEntryPoint(entryPoint);
	    	http.authenticationProvider(daoAuthenticationProvider()	);
	    	http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	    	DefaultSecurityFilterChain build = http.build();
	    	return build;
	   }
	
	@Bean    
	public DaoAuthenticationProvider daoAuthenticationProvider() {
	
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(this.customerDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder(10);  
	}
	
	@Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration cofiguration) throws Exception {
        return cofiguration.getAuthenticationManager();
    }
}