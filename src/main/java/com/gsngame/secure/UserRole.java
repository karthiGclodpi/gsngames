package com.gsngame.secure;

public class UserRole {

	 public final String username;
     public final String role;
     public final String password;
     
     public UserRole(String username, String password, String role) {
         this.username = username;
         this.password = password;
         this.role = role;
     }
}
