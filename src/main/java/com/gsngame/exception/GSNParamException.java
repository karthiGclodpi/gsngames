package com.gsngame.exception;


import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


import org.glassfish.jersey.server.ParamException;
import org.springframework.stereotype.Component;

@Component
@Provider
public class GSNParamException implements ExceptionMapper<ParamException> {

	@Override
	public Response toResponse(ParamException exception) {
		Error error = new Error();
		error.setErrorMessage("URL Not Found");

		return Response.status(Status.NOT_FOUND).entity(error).build();
	}

}
