package com.gsngame.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gsngame.dao.UserDAO;
import com.gsngame.data.User;
import com.gsngame.exception.GSNBadRequestException;
import com.gsngame.exception.GSNConflictException;

@Component
public class UserBOImpl implements UserBO {

	@Autowired
	private UserDAO userDAO;

	@Override
	public User user(User user) {
		if (userDAO.validateRole(user.getRoleid())) {
			if (!userDAO.checkUserExist(user.getUsername()))
				user.setId(userDAO.user(user));
			else
				throw new GSNConflictException("username already exist");
		} else {
			throw new GSNBadRequestException("roleid is not valid");
		}
		return user;
	}

}
