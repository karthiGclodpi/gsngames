package com.gsngame.secure;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

import com.gsngame.data.Role;
import com.gsngame.data.User;

public class UserAuthorization implements SecurityContext {

	private User user;
	private Role role;
	
	UserAuthorization(User user,Role role)
	{
		this.user=user;
		this.role=role;
	}

	@Override
	public String getAuthenticationScheme() {
		System.out.println("Basic");
		return "Basic";

	}

	@Override
	public Principal getUserPrincipal() {
		return new Principal() {
			@Override
			public String getName() {
				return Integer.toString(user.getId());
			}
		};
	}

	@Override
	public boolean isSecure() {
		return true;
	}

	@Override
	public boolean isUserInRole(String validRole) {
		return role.getName().equals(validRole);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
