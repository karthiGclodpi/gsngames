package com.gsngame.exception;




import javax.ws.rs.ext.ContextResolver;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;


public class GSNJacksonJsonProvider implements ContextResolver<ObjectMapper>{

	private final ObjectMapper mapper;

	public GSNJacksonJsonProvider() {
		super();
		mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		//mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		// mapper.enable(MapperFeature.USE_WRAPPER_NAME_AS_PROPERTY_NAME);
		// mapper.setDateFormat(new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'ZZZ (z)"));

		// Setting de-serialization properties to configure
		mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
		mapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
		mapper.configure(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS, true);
		//mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);

		mapper.setAnnotationIntrospector(createJaxbJacksonAnnotationIntrospector());
	}

	private static AnnotationIntrospector createJaxbJacksonAnnotationIntrospector() {

		final AnnotationIntrospector jaxbIntrospector = new JaxbAnnotationIntrospector(TypeFactory.defaultInstance());
		final AnnotationIntrospector jacksonIntrospector = new JacksonAnnotationIntrospector();

		return AnnotationIntrospector.pair(jacksonIntrospector, jaxbIntrospector);
	}
	
	@Override
	public ObjectMapper getContext(Class<?> objectType) {
		return mapper;
	}

}
