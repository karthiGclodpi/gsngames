package com.gsngame.api;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gsngame.business.TitleBO;
import com.gsngame.data.Title;
import com.gsngame.exception.Error;

@Path("/v1")
@Component
public class TitleService {

	@Autowired
	private TitleBO titleBO;

	@POST
	@Path("/title")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@RolesAllowed({ "ADMIN" })
	public Response title(@Context SecurityContext securityContext,
			@NotNull(message = "Provide Valid Title") @Valid final Title title) {
		System.out.println(securityContext.getUserPrincipal().getName());
		title.setCreatedbyuserid(Integer.parseInt(securityContext
				.getUserPrincipal().getName()));
		return Response.status(Status.CREATED).entity(titleBO.title(title))
				.build();
	}

	@GET
	@Path("/title")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@RolesAllowed({ "ADMIN","MEMBER"})
	public Response title(
			@Context SecurityContext securityContext,
			@DefaultValue("0") @QueryParam("status") String status) {
		if(status.equals("0"))
		{
			return Response.status(Status.OK).entity(titleBO.titles()).build();
		}
		else
		{
			if(titleBO.validateStatus(status))
			{
				if(!status.equals("NEW")){
					List<Title> titles=titleBO.titleByUserIdandStatus(Integer.parseInt(securityContext
							.getUserPrincipal().getName()),status);
					if(titles.size()>0)
					{
						return Response.status(Status.OK).entity(titles).build();
					}
				}
				else
				{
					return Response.status(Status.OK).entity(titleBO.unsubscribedTitleByUserId(Integer.parseInt(securityContext
							.getUserPrincipal().getName()))).build();
				}
			}
			else
			{
				Error  error = new Error();
				error.setErrorMessage("Valid Status : "+titleBO.validStatus() );
				return Response.status(Status.BAD_REQUEST).entity(error).build();
			}
		}
		return null;
	}

}
