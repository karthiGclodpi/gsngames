package com.gsngame.api;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gsngame.business.UserBO;
import com.gsngame.data.User;


@Path("/v1")
@Component
public class UserService {

	@Autowired
	private UserBO userBO;
	
	@POST
	@Path("/user")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@RolesAllowed({"ADMIN"})
	public Response user(@Context SecurityContext securityContext,
			@NotNull(message = "Provide Valid User") @Valid final User user) {
		
		return Response.status(Status.CREATED).entity(userBO.user(user)).build();
	}

}
