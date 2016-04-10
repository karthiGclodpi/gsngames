package com.gsngame.exception;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

@Component
@Provider
public class GSNNotFoundException implements ExceptionMapper<NotFoundException> {

	@Override
	public Response toResponse(NotFoundException exception) {
		Error error=new Error();
		error.setErrorMessage("URL Not Found");
		
		return Response.status(Status.NOT_FOUND).entity(error).build();
	}

}
