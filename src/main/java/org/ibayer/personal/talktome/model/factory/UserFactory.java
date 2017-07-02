package org.ibayer.personal.talktome.model.factory;

import java.util.UUID;

import org.ibayer.personal.talktome.model.User;

/**
 * Factory class to instantiate a new User
 * @author ibrahim.bayer
 *
 */
public class UserFactory {

	private UserFactory() {
	}
	
	/**
	 * Static factory method to instantiate a new user. User id is generated randomly using {@link UUID}
	 * @return
	 */
	public static User newUser(){
		User user =  new User();
		user.setUserId(UUID.randomUUID().toString());
		return user;
	}
}
