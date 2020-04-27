package com.projet.gateway.controller;

import com.projet.gateway.model.User;
import com.projet.gateway.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

	@Autowired
	private UserService userService;


	@GetMapping("/")
	public String hi() {
		return  "hi i am the gateway";
	}


	@GetMapping("/users")
	public List<User> findAllUser() {
		return  userService.findUsers();
	}

	@GetMapping("/users/{userId}")
	public User findAUser(@PathVariable Integer userId) {
		return  userService.findUser(userId);
	}


	@PostMapping("/users")
	public ResponseEntity<User> adduSer(@RequestBody User userNew) {
		//reste a changer si le customer existe deja on ne le rajout pas
		System.out.println(userNew);
		if(this.userService.checkIfUsernameExist(userNew.getUserName()))
			return new ResponseEntity<User>(HttpStatus.CONFLICT);

		User user = this.userService.addUser(userNew);
		if (user != null && user.getId()!= null){

			return new ResponseEntity<User>(user, HttpStatus.OK);

		}

		return new ResponseEntity<User>(HttpStatus.NOT_MODIFIED);

	}

	@PutMapping("/users")
	public ResponseEntity<User> updateUser(@RequestBody User userNew) {

		if (!userService.checkIfIdexists(userNew.getId())) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}

		User user = this.userService.updatedUser(userNew);
		if (user != null){

			return new ResponseEntity<User>(user, HttpStatus.OK);

		}

		return new ResponseEntity<User>(HttpStatus.NOT_MODIFIED);

	}


	@DeleteMapping("/users/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable Integer userId) {

		this.userService.deleteUser(userId);
		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
	}





}
