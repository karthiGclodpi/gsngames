package com.gsngame.exception;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

@Provider
@Component
public class GSNConstraintViolationExceptionHandler implements
		ExceptionMapper<ConstraintViolationException> {

	@Override
	public Response toResponse(ConstraintViolationException violationException) {
		violationException.printStackTrace();
		
		List<Error> errors = new ArrayList<Error>();
		for (ConstraintViolation constraintViolation : violationException
				.getConstraintViolations()) {
			Error error = new Error();
			error.setErrorMessage(constraintViolation.getMessageTemplate());
			System.out.println(constraintViolation);
			errors.add(error);
		}

		return Response.status(Status.BAD_REQUEST).entity(errors).build();
	}

}
