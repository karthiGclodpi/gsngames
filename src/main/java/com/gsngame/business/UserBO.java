package com.gsngame.business;

import org.springframework.stereotype.Component;

import com.gsngame.data.User;

@Component
public interface UserBO {

	public User user(User user);

}
