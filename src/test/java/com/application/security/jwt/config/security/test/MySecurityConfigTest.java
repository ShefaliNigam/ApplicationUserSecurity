package com.application.security.jwt.config.security.test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.application.security.jwt.config.JwtAuthenticationEntryPoint;
import com.application.security.jwt.config.JwtAuthenticationFilter;
import com.application.security.jwt.config.MySecurityConfig;
import com.application.security.jwt.service.JwtCustomUserDetailsService;

@SpringBootTest
@Import(MySecurityConfig.class)
public class MySecurityConfigTest {

    @Mock
    private JwtCustomUserDetailsService customUserDetailsService;

    @Mock
    private JwtAuthenticationFilter jwtFilter;

    @Mock
    private JwtAuthenticationEntryPoint entryPoint;

    @InjectMocks
    private MySecurityConfig mySecurityConfig;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new Object())
              //  .apply(springSecurity())  // Set up Spring Security for testing
                .build();
    }

    @Test
    void testPublicEndpointsAllowed() throws Exception {
		/*
		 * // mockMvc.perform(get("/swagger-ui/index.html"))
		 * .andExpect(status().isOk());
		 * 
		 * mockMvc.perform(("/v3/api-docs")) .andExpect(status().isOk());
		 * 
		 * mockMvc.perform(post("/user/add")) .andExpect(status().isOk());
		 * 
		 * mockMvc.perform(get("/user/id/getAll")) .andExpect(status().isOk());
		 */
    }

    @Test
    void testUnauthorizedAccessToProtectedEndpoint() throws Exception {
       // mockMvc.perform(get("/user/some-protected-endpoint"))
               // .andExpect(status().isUnauthorized());
    }

	/*
	 * @Test
	 * 
	 * @WithMockUser(username = "admin", roles = {"ADMIN"}) void
	 * testAuthorizedAccessWithMockUser() throws Exception {
	 * mockMvc.perform(get("/user/some-protected-endpoint"))
	 * .andExpect(status().isOk()); }
	 */
    @Test
    void testAuthenticationManagerBean() throws Exception {
       // AuthenticationManager authManager = mySecurityConfig.authenticationManagerBean(null);
       // assert authManager != null;
    }

    @Test
    void testPasswordEncoder() {
        String rawPassword = "password123";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        assert passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Test
    void testDaoAuthenticationProvider() {
        DaoAuthenticationProvider provider = mySecurityConfig.daoAuthenticationProvider();
        assert provider != null;
		/*
		 * assert provider.getUserDetailsService() == customUserDetailsService; assert
		 * provider.getPasswordEncoder() instanceof BCryptPasswordEncoder;
		 */
    }
}
