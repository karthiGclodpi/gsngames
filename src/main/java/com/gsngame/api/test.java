package com.gsngame.api;


import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gsngame.data.User;



@Path("/v1")
@Component
public class test {
	
	@Autowired
	private testet ttd;

	@Path("/test")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,MediaType.TEXT_PLAIN })
	@RolesAllowed({"ADMIN"})
	public Response testst()
	{
		User u=new User();
		u.setId(0);
		ttd.test();
		return Response.status(Status.OK).entity(u).build();
	}
}
