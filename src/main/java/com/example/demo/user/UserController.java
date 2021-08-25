package com.example.demo.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserController {
	
	@Autowired
	UserDaoService userDaoService;
	
	//Return the status as created(201) and return the URI of newly created user in response header
	//ResponseEntity of Spring is extension of HttpEntity which adds a HttpStatus code
	//Validate the request based upon validation constraints present in User class 
	@PostMapping("users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = userDaoService.createUser(user);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
											 .path("/{id}")
											 .buildAndExpand(savedUser.getId())
											 .toUri();
		
		return ResponseEntity.created(uri).build(); //uri location would be set in response header
	}
		
	@GetMapping("users")
	public List<User> getAllUsers(){
		return userDaoService.findAllUsers();
	}
	
	/*@GetMapping("users/{id}")
	public User getUser(@PathVariable int id){
		User user = userDaoService.findUser(id);
		if(user == null) throw new UserNotFoundException("id-"+ id);
		return user;
	}*/
	
	
	/*
	 * Rather than simply returning user, we can also return some other information in the form of navigable links along with user data. 
	 * Using HATEOAS we can include other actions which can be performed on returned data.
	 * For example, we can send link to get all users
	 * So we can add link to user resource using HATEOAS: e.g. to add link to getAllUsers
	 */
	@GetMapping("users/{id}")
	public EntityModel<User> getUser(@PathVariable int id){
		User user = userDaoService.findUser(id);
		if(user == null) throw new UserNotFoundException("id-"+ id);
		
		//Adding links to user resource using HATEOAS: e.g. to add link to getAllUsers
		EntityModel<User> model = EntityModel.of(user);
		WebMvcLinkBuilder linkToUsers = linkTo(methodOn(this.getClass()).getAllUsers());
		model.add(linkToUsers.withRel("all-users"));
		
		return model;
	}
	
	@DeleteMapping("users/{id}")
	public void deleteUser(@PathVariable int id){
		User user = userDaoService.deleteUser(id);
		if(user == null) throw new UserNotFoundException("id-"+ id);
	}
}
