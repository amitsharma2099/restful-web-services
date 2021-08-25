package com.example.demo.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
public class UserJPAController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PostRepository postRepo;
	
	//Return the status as created(201) and return the URI of newly created user in response header
	//ResponseEntity of Spring is extension of HttpEntity which adds a HttpStatus code
	//Validate the request based upon validation constraints present in User class 
	@PostMapping("/jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = userRepo.save(user);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
											 .path("/{id}")
											 .buildAndExpand(savedUser.getId())
											 .toUri();
		
		return ResponseEntity.created(uri).build(); //uri location would be set in response header
	}
		
	@GetMapping("/jpa/users")
	public List<User> getAllUsers(){
		return userRepo.findAll();
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
	@GetMapping("/jpa/users/{id}")
	public EntityModel<User> getUser(@PathVariable int id){
		Optional<User> optinalUser = userRepo.findById(id);
		if(!optinalUser.isPresent()) throw new UserNotFoundException("id-"+ id);
		
		//Adding links to user resource using HATEOAS: e.g. to add link to getAllUsers
		EntityModel<User> model = EntityModel.of(optinalUser.get());
		WebMvcLinkBuilder linkToUsers = linkTo(methodOn(this.getClass()).getAllUsers());
		model.add(linkToUsers.withRel("all-users"));
		
		return model;
	}
	
	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id){
		userRepo.deleteById(id);
	}
	
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> getAllPostsOfUser(@PathVariable int id){
		Optional<User> optinalUser = userRepo.findById(id);
		
		if(!optinalUser.isPresent()) throw new UserNotFoundException("id-"+ id);
		
		return optinalUser.get().getPosts();
	}
	
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPost(@PathVariable int id, @RequestBody Post post) {
		Optional<User> optinalUser = userRepo.findById(id);
		if(!optinalUser.isPresent()) throw new UserNotFoundException("id-"+ id);
		
		User user = optinalUser.get();
		post.setUser(user);
		postRepo.save(post);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
											 .path("/{id}")
											 .buildAndExpand(post.getId())
											 .toUri();
		
		return ResponseEntity.created(uri).build(); //uri location would be set in response header
	}
}
