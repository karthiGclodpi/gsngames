package com.gsngame.exception;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

@Component
@Provider
public class GSNNotAllowedException implements ExceptionMapper<NotAllowedException>{

	@Override
	public Response toResponse(NotAllowedException exception) {
		Error error = new Error();
		error.setErrorMessage("Method Not Allowed");
		return Response.status(Status.METHOD_NOT_ALLOWED).entity(error).build();
	}

}
