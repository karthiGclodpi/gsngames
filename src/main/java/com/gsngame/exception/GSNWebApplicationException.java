package com.gsngame.exception;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

@Provider
@Component
public class GSNWebApplicationException implements
		ExceptionMapper<WebApplicationException> {

	@Override
	public Response toResponse(WebApplicationException wae) {
		Error error = new Error();
		wae.printStackTrace();
		System.out.println(wae.getMessage());
		error.setErrorMessage("Internal Server Error");
		if (wae instanceof ForbiddenException) {
			error.setErrorMessage(wae.getMessage());
			return Response.status(Status.FORBIDDEN).entity(error).build();
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(error)
				.build();
	}

}
