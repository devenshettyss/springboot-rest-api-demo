package com.tcs.springbootdemo.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcs.springbootdemo.entity.User;
import com.tcs.springbootdemo.exception.UserNotFoundException;
import com.tcs.springbootdemo.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserController { // spring bean, act as request receiver
	
	 private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired // DI
	IUserService userService;

	@GetMapping
	private Iterable<User> getUser() {
		return userService.getAllUsers();
	}

	@GetMapping("/{id}")
	private Optional<User> getUser(@PathVariable("id") Integer id) {
		return userService.getUser(id);
	}

	@ExceptionHandler(value = { UserNotFoundException.class, IllegalStateException.class })
	public ResponseEntity<User> exception(UserNotFoundException userNotFoundException) {
		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}

	@PostMapping
	private void saveUser(@RequestBody @Valid User user) {
		try {
			userService.save(user);
		} catch (Exception e) {
			logger.error(e.getCause().toString());
		}
		logger.debug(user.getFirstName());
	}

	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable("id") Integer id) {
		userService.deleteUser(id);
	}
//	@PutMapping // METHOD+Path
//	private void updateUser(@RequestBody User user) {
//		userService.save(user);
//		System.out.println(user.getFirstName());
//	}
}