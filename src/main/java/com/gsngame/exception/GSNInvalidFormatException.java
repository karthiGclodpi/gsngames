package com.gsngame.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@Provider
@Component
public class GSNInvalidFormatException implements
		ExceptionMapper<InvalidFormatException> {

	@Override
	public Response toResponse(InvalidFormatException ife) {

		Error error = new Error();
		error.setErrorMessage("Value:" + ife.getValue() + " is invalid for "
				+ ife.getPath().get(0).getFieldName());
		return Response.status(Status.BAD_REQUEST).entity(error).build();
	}

}
