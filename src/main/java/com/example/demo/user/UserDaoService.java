package com.example.demo.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {

	private static List<User> users = new ArrayList<>();
	private int userCount = 3;
	
	static {
		users.add(new User(1, "Vijay", new Date()));
		users.add(new User(2, "Aakash", new Date()));
		users.add(new User(3, "Ankur", new Date()));
	}
	
	public User createUser(User user) {
		if(user.getId() == null) {
			user.setId(++userCount);
		}
		users.add(user);
		return user;
	}
	
	public List<User> findAllUsers(){
		return users;
	}
	
	public User findUser(int id){
		return users.stream().filter(user -> user.getId() == id).findFirst().orElse(null);
	}
	
	public User deleteUser(int id){
		Iterator<User> iterator = users.iterator();
		while(iterator.hasNext()) {
			User user = iterator.next();
			if(user.getId() == id) {
				iterator.remove();
				return user;
			}
		}
		return null;
	}
	
	
}
