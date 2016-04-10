package com.gsngame.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

@Provider
@Component
public class GSNExceptionHandler implements ExceptionMapper<GSNException> {

	@Override
	public Response toResponse(GSNException gsnException) {
		gsnException.printStackTrace();
		Error error = new Error();
		if (gsnException instanceof GSNBadRequestException) {
			error.setErrorMessage(gsnException.getErrorMessage());
			return Response.status(Status.BAD_REQUEST).entity(error).build();
		} else if (gsnException instanceof GSNConflictException) {
			error.setErrorMessage(gsnException.getErrorMessage());
			return Response.status(Status.CONFLICT).entity(error).build();
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(error)
				.build();
	}

}
