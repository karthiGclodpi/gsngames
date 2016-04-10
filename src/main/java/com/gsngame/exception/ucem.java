package com.gsngame.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.spi.ExtendedExceptionMapper;
import org.springframework.stereotype.Component;

@Provider
@Component
public class ucem implements ExtendedExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable arg0) {
		arg0.printStackTrace();
		return null;
	}

	@Override
	public boolean isMappable(Throwable exception) {
		exception.printStackTrace();
		return true;
	}

}
