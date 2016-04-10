package com.gsngame.dao;

import org.springframework.stereotype.Component;

import com.gsngame.data.Role;
import com.gsngame.data.User;

@Component
public interface UserDAO {

	String VALIDATE_ROLE = "select count(*) from role where id=:roleid";

	String SEQUENCE = "select auto_increment from information_schema.tables";

	String USER_SEQUENCE = SEQUENCE + " WHERE table_name = 'user'";
	
	String USER_ALREADY_EXIST= "select count(*) from user where username=:username";
	
	String USER_BY_USERNAME="select id,username,password,roleid from user where username=:username";
	
	String ROLE_BY_ID="select id,name from role where id=:roleid";

	public boolean validateRole(int roleid);

	public int user(User user);

	public boolean checkUserExist(String username);

	public User getUserByUsername(String username);

	public Role getRoleById(int roleid);

}
