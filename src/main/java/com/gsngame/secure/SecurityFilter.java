package com.gsngame.secure;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gsngame.dao.UserDAO;
import com.gsngame.data.Role;
import com.gsngame.data.User;
import com.gsngame.exception.Error;

@Provider
@Component
@Priority(Priorities.AUTHENTICATION)
public class SecurityFilter implements ContainerRequestFilter {

	private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
	private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";
	private static final String SECURED_URL_PREFIX = "v1";

	@Autowired
	private UserDAO userDAO;

	@Override
	public void filter(ContainerRequestContext requestContext)
			throws IOException {

		if (requestContext.getUriInfo().getPath().contains(SECURED_URL_PREFIX)) {
			if (requestContext.getHeaders().containsKey(
					AUTHORIZATION_HEADER_KEY)) {
				List<String> authHeader = requestContext.getHeaders().get(
						AUTHORIZATION_HEADER_KEY);
				if (authHeader.size() > 0) {
					String authToken = authHeader.get(0);
					authToken = authToken.replace(AUTHORIZATION_HEADER_PREFIX,
							"");
					String decodedString = Base64.decodeAsString(authToken);
					StringTokenizer tokenizer = new StringTokenizer(
							decodedString, ":");
					String username = null;
					String password = null;
					if (tokenizer.countTokens() > 1) {
						username = tokenizer.nextToken();
						password = tokenizer.nextToken();
					}
					User user = userDAO.getUserByUsername(username);
					if (!(user == null)) {
						Role role = userDAO.getRoleById(user.getRoleid());
						if (user.getPassword().equals(password)) {
							requestContext
									.setSecurityContext(new UserAuthorization(
											user, role));
							return;
						} else {

						}
					}
				}
			}
		}
		Error error = new Error();
		error.setErrorMessage("Invalid username and password");
		Response response = Response.status(Status.UNAUTHORIZED).entity(error)
				.build();

		requestContext.abortWith(response);

	}

}
