package org.ibayer.personal.talktome.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.ibayer.personal.talktome.exception.ResourceNotException;
import org.ibayer.personal.talktome.model.User;
import org.ibayer.personal.talktome.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * Service class to manage users. Supports get and post operation over user
 * @author ibrahim.bayer
 *
 */
@Service
public class UserService {
	
	private final UserRepository userRepository;
	
	/**
	 * @param userRepository
	 */
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * @param userId
	 * @return
	 */
	public User get(String userId) {
		User user = userRepository.findByUserId(userId);
		if (user != null){
			return user;
		}
		throw new ResourceNotException("User not found");
	}

	/**
	 * Creates a new user.
	 * @param user
	 * @return
	 */
	public void post(User user) {
		user.setUserId(UUID.randomUUID().toString());
		userRepository.save(user);
	}

	/**
	 * Searches a new user
	 * @param name
	 * @return
	 */
	public List<User> search(String name) {
		return Arrays.asList(userRepository.findByName(name));
	}
	
}
