package com.users.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.users.exception.ResourceNotFoundException;
import com.users.model.User;
import com.users.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTests {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService = new UserService();
	
	private User user;
	
	@Before
	public void setUp() {
		user = new User("Eric", "Simmons", "Eric@xyz.com");
	}

	@Test
	public void testFindAll() {
		List<User> users = Arrays.asList(new User("Eric", "Simmons", "Eric@xyz.com"));
		Mockito.when(userRepository.findAll()).thenReturn(users);
		List<User> actualUsers = userService.findAll();
		Assert.assertEquals(users.size(), actualUsers.size());
	}

	@Test
	public void testFindById() throws ResourceNotFoundException {
		Mockito.when(userRepository.findById(1l)).thenReturn(Optional.of(user));
		User actualUser = userService.findById(1l);
		Assert.assertEquals(user, actualUser);

	}

	@Test
	public void testSave() {
		Mockito.when(userRepository.save(user)).thenReturn(user);
		User actualUser = userService.save(user);
		Assert.assertEquals(user, actualUser);
	}

	@Test
	public void testUpdateUser() throws ResourceNotFoundException {
		User updatedUser = new User("Eric", "Simmons", "Eric@abc.com");
		Mockito.when(userRepository.findById(1l)).thenReturn(Optional.of(user));
		Mockito.when(userRepository.save(user)).thenReturn(updatedUser);
		User actualUser = userService.updateUser(1l, updatedUser);
		Assert.assertEquals(updatedUser, actualUser);
	}

	@Test
	public void testDeleteUser()
			throws ResourceNotFoundException {
		Mockito.when(userRepository.findById(1l)).thenReturn(Optional.of(user));
		Map<String, Boolean> response = userService.deleteUser(1l);
		assert response.get("deleted");
	}

}
