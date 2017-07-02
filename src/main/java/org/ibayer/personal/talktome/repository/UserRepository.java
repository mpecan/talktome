package org.ibayer.personal.talktome.repository;

import org.ibayer.personal.talktome.model.User;
import org.springframework.cache.Cache;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * Repository implementation to manage users based on spring {@link Cache}
 * 
 * @author ibrahim.bayer
 *
 */
@Component
public interface UserRepository extends CrudRepository<User, Long> {

	/**
	 * Finds existing user by name
	 * @param name to search
	 * @return matching user
	 */
	User findByName(String name);

	/**
	 * Finds existing user by resource id
	 * @param userId to search
	 * @return matching user
	 */
	User findByUserId(String userId);

}
