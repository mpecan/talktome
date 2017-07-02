package org.ibayer.personal.talktome.service;

import java.util.List;

import org.ibayer.personal.talktome.exception.ResourceNotException;
import org.ibayer.personal.talktome.model.User;
import org.ibayer.personal.talktome.model.factory.UserFactory;
import org.ibayer.personal.talktome.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

public class TestUserService {
	
	@Spy
	UserService userService;
	
	@Mock
	UserRepository userRepository = Mockito.mock(UserRepository.class);
	
	@Before
	public void initTest(){
		userService = new UserService(userRepository);
	}
	
	@Test
	public void testUserServiceGet(){
		User expectedUser = UserFactory.newUser();
		Mockito.when(userRepository.findByUserId(Mockito.anyString())).thenReturn(expectedUser);
		User actualUser = userService.get(Mockito.anyString());
		Assert.assertNotNull(actualUser);
		Assert.assertEquals(expectedUser, actualUser);
	}
	
	@Test(expected = ResourceNotException.class)
	public void testUserServiceGetNullOf(){
		Mockito.when(userRepository.findByUserId(Mockito.anyString())).thenReturn(null);
		userService.get(Mockito.anyString());
	}
	
	@Test
	public void testUserServicePost(){
		User user = UserFactory.newUser();
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
		userService.post(user);
		Assert.assertNotNull(user.getUserId());
	}
	
	@Test
	public void testUserGetByName(){
		User expectedUser = UserFactory.newUser();
		Mockito.when(userRepository.findByName(Mockito.anyString())).thenReturn(expectedUser);
		List<User> actualUser = userService.search(Mockito.anyString());
		Assert.assertNotNull(actualUser);
		Assert.assertEquals(Integer.valueOf(1), Integer.valueOf(actualUser.size()));
		Assert.assertEquals(expectedUser, actualUser.get(0));
	}

}
