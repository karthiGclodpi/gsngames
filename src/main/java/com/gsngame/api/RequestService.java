package com.gsngame.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gsngame.business.RequestBO;
import com.gsngame.business.TitleBO;
import com.gsngame.data.AdminAction;
import com.gsngame.data.Link;
import com.gsngame.data.Request;
import com.gsngame.exception.Error;
import com.gsngame.exception.GSNBadRequestException;

@Path("/v1")
@Component
public class RequestService {

	@Autowired
	private RequestBO requestBO;

	@Autowired
	private TitleBO titleBO;

	@Context
	UriInfo uriInfo;

	@POST
	@Path("/request")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@RolesAllowed({ "ADMIN", "MEMBER" })
	public Response request(
			@Context SecurityContext securityContext,
			@NotNull(message = "Provide Valid Title") @Valid final Request request) {

		request.setUserid(Integer.parseInt(securityContext.getUserPrincipal()
				.getName()));
		return Response.status(Status.CREATED)
				.entity(requestBO.request(request)).build();

	}

	@GET
	@Path("/request")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@RolesAllowed({ "ADMIN" })
	public Response request(
			@DefaultValue("0") @QueryParam("status") String status) {

		String absolutePath = uriInfo.getBaseUri().toString();

		List<Request> requests = new ArrayList<Request>();

		if (status.equals("0")) {
			
			List<Link> links = null;
			for (Request req : requestBO.request()) {
				links = new ArrayList<Link>();
				Link link = new Link();
				link.setRel("title");
				link.setHref(absolutePath + "v1/title/" + req.getTitleid());
				links.add(link);
				link = new Link();
				link.setRel("user");
				link.setHref(absolutePath + "v1/user/" + req.getUserid());
				links.add(link);
				req.setLinks(links);
				requests.add(req);
			}

		} else {
			if (titleBO.validateStatus(status)) {
				List<Link> links = null;
				for (Request req : requestBO.request(status)) {
					links = new ArrayList<Link>();
					Link link = new Link();
					link.setRel("title");
					link.setHref(absolutePath + "v1/title/" + req.getTitleid());
					links.add(link);
					link = new Link();
					link.setRel("user");
					link.setHref(absolutePath + "v1/user/" + req.getUserid());
					links.add(link);
					req.setLinks(links);
					requests.add(req);
				}

			} else {
				Error error = new Error();
				error.setErrorMessage("Valid Status : " + titleBO.validStatus());
				return Response.status(Status.BAD_REQUEST).entity(error)
						.build();
			}
		}
		if (requests.size() > 0) {
			return Response.status(Status.OK).entity(requests).build();
		}
		return null;
	}

	@PUT
	@Path("/request/{id}/{action}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@RolesAllowed({ "ADMIN" })
	public Response request(@Context SecurityContext securityContext,
			@PathParam("id") int id, @PathParam("action") String action) {

		try {
			requestBO.request(id, AdminAction.valueOf(action).getStatus());
			return request(securityContext, id);
		} catch (Exception e) {
			String errorMsg = "Invalid action: " + action
					+ ", valid actions are : "
					+ Arrays.asList(AdminAction.class.getEnumConstants());
			throw new GSNBadRequestException(errorMsg);
		}

	}

	@GET
	@Path("/request/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@RolesAllowed({ "ADMIN" })
	public Response request(@Context SecurityContext securityContext,
			@PathParam("id") int id) {

		List<Link> links = null;
		List<Request> requests = new ArrayList<Request>();
		String absolutePath = uriInfo.getBaseUri().toString();
		for (Request req : requestBO.request(id)) {
			links = new ArrayList<Link>();
			Link link = new Link();
			link.setRel("title");
			link.setHref(absolutePath + "v1/title/" + req.getTitleid());
			links.add(link);
			link = new Link();
			link.setRel("user");
			link.setHref(absolutePath + "v1/user/" + req.getUserid());
			links.add(link);
			req.setLinks(links);
			requests.add(req);
		}
		if (requests.size() > 0) {
			return Response.status(Status.OK).entity(requests).build();
		}
		return null;

	}
}
