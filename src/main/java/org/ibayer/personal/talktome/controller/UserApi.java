package org.ibayer.personal.talktome.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import javax.validation.Valid;

import org.ibayer.personal.talktome.model.User;
import org.ibayer.personal.talktome.service.UserService;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Api to manage users. Supports GET,POST http methods.
 * 
 * @author ibrahim.bayer
 *
 */
@RestController
@RequestMapping("/users")
public class UserApi {

	private final UserService userService;

	public UserApi(final UserService userService) {
		this.userService = userService;
	}

	/**
	 * Retrieves an existing user.
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	@ApiOperation(value = "Retrieves an existing user", notes = "Retrieves an existing user", tags = { "Users" })
	@ApiResponses({ @ApiResponse(code = 200, message = "Returned!", response = User.class) })
	public ResponseEntity<Resource<User>> get(@PathVariable String userId) {
		User user = userService.get(userId);
		Resource<User> resource = new Resource<>(user);
		resource.add(linkTo(methodOn(UserApi.class).get(user.getUserId())).withSelfRel());
		return new ResponseEntity<>(resource, HttpStatus.OK);
	}

	/**
	 * Searches user by input parameters. Currently implemented search by name
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "Search an existing user", notes = "Search an existing user", tags = { "Users" })
	@ApiResponses({ @ApiResponse(code = 200, message = "Returned!", response = User.class) })
	public ResponseEntity<Resources<User>> search(@RequestParam String name) {
		List<User> user = userService.search(name);
		Resources<User> resources = new Resources<>(user);
		return new ResponseEntity<>(resources, HttpStatus.OK);
	}

	/**
	 * Creates a new user
	 * @param user
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "Creates a new user", notes = "Creates a new user", tags = { "Users" })
	@ApiResponses({ @ApiResponse(code = 201, message = "Saved!", response = User.class) })
	public ResponseEntity<Resource<User>> post(@Valid @RequestBody User user) {
		userService.post(user);
		Resource<User> resource = new Resource<>(user);
		resource.add(linkTo(methodOn(UserApi.class).get(user.getUserId())).withSelfRel());
		return new ResponseEntity<>(resource, HttpStatus.CREATED);
	}

}
