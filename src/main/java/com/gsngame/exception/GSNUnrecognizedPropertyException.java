package com.gsngame.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;;

@Provider
@Component
public class GSNUnrecognizedPropertyException implements
		ExceptionMapper<UnrecognizedPropertyException> {

	@Override
	public Response toResponse(UnrecognizedPropertyException upe) {

		Error error = new Error();
		error.setErrorMessage("Unrecognized Field : "+upe.getPropertyName());
		return Response.status(Status.BAD_REQUEST).entity(error).build();
	}

}
