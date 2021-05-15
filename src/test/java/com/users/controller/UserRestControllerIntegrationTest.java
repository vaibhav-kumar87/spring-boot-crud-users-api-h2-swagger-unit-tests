package com.users.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.gson.Gson;
import com.users.ManageUsersApplication;
import com.users.model.User;
import com.users.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManageUsersApplication.class)
@AutoConfigureMockMvc
public class UserRestControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private UserService userService;

	@Autowired
	private Gson gson;

	private User user;

	@Before
	public void setUp() {
		user = new User("Eric", "Simmons", "Eric@xyz.com");
	}

	@Test
	public void givenUsers_whenGetALLUsers_thenStatus200() throws Exception {
		List<User> allUsers = Arrays.asList(user);

		given(userService.findAll()).willReturn(allUsers);

		String uri = "/api/v1/users";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		verify(userService, VerificationModeFactory.times(1)).findAll();
		reset(userService);
	}

	@Test
	public void givenUser_whenGetUserByID_thenStatus200() throws Exception {

		given(userService.findById(1l)).willReturn(user);

		String uri = "/api/v1/users/1";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assert content.contains("Eric");

		verify(userService, VerificationModeFactory.times(1)).findById(1l);
		reset(userService);
	}

	@Test
	public void givenUser_whenCreateUser_thenStatus200() throws Exception {
		given(userService.save(any(User.class))).willReturn(user);
		String uri = "/api/v1/users";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(gson.toJson(user))).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assert content.contains("Eric");

		verify(userService, VerificationModeFactory.times(1)).save(any(User.class));
		reset(userService);
	}

	@Test
	public void givenUser_whenUpdateUser_thenStatus200() throws Exception {

		given(userService.updateUser(any(Long.class),any(User.class))).willReturn(user);

		String uri = "/api/v1/users/1";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(gson.toJson(user))).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assert content.contains("Eric");

		verify(userService, VerificationModeFactory.times(1)).updateUser(any(Long.class), any(User.class));
		reset(userService);
	}

	@Test
	public void givenUser_whenDeleteUserByID_thenStatus200()
	  throws Exception {
		Map<String, Boolean> is_deleted = new HashMap<String, Boolean>();
		is_deleted.put("deleted", Boolean.TRUE);

        given(userService.deleteUser(1l)).willReturn(is_deleted);
		
	    String uri = "/api/v1/users/1";
	    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
	       .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	    
	    int status = mvcResult.getResponse().getStatus();
	    assertEquals(200, status);
	    
	    verify(userService, VerificationModeFactory.times(1)).deleteUser(1l);
        reset(userService);
	}

}