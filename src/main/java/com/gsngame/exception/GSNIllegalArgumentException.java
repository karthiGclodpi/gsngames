package com.gsngame.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;



public class GSNIllegalArgumentException implements ExceptionMapper<IllegalArgumentException> {

	@Override
	public Response toResponse(IllegalArgumentException exception) {
		System.out.println(exception);
		return null;
	}

}
