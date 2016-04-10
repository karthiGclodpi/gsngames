package com.gsngame.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gsngame.dao.RequestDAO;

import com.gsngame.data.Request;
import com.gsngame.data.RequestStatus;
import com.gsngame.exception.GSNBadRequestException;
import com.gsngame.exception.GSNConflictException;

@Component
public class RequestBOImpl implements RequestBO {

	@Autowired
	private RequestDAO requestDAO;

	@Autowired
	private TitleBO titleBO;

	@Override
	public Request request(Request request) {

		if (titleBO.checkTitleIdExist(request.getTitleid())) {
			if (!requestDAO.checkRequestExist(request.getUserid(),
					request.getTitleid())) {
				request.setStatus("PENDING");
				request.setId(requestDAO.request(request));
				// request.setStatus(requestDAO.statusByRequestId(request.getId()));
			} else
				throw new GSNConflictException("Request already exist");
		} else {
			throw new GSNBadRequestException("titleid does not exist");
		}
		return request;
	}

	@Override
	public List<Request> request() {
		Map<Integer, String> statusMap = status();
		List<Request> requests = new ArrayList<Request>();

		for (Request request : requestDAO.request()) {
			request.setStatus(statusMap.get(request.getStatusid()));
			requests.add(request);
		}
		return requests;
	}

	private Map<Integer, String> status() {
		Map<Integer, String> statusMap = new HashMap<Integer, String>();
		for (RequestStatus requestStatus : requestDAO.requestStatus()) {
			statusMap.put(requestStatus.getId(), requestStatus.getStatus());
		}
		return statusMap;
	}

	@Override
	public List<Request> request(String status) {
		Map<Integer, String> statusMap = status();
		List<Request> requests = new ArrayList<Request>();

		for (Request request : requestDAO.request(status)) {
			request.setStatus(statusMap.get(request.getStatusid()));
			requests.add(request);
		}
		return requests;
	}

	@Override
	public List<Request> request(int requestid) {

		Map<Integer, String> statusMap = status();
		List<Request> requests = new ArrayList<Request>();
		for (Request request : requestDAO.request(requestid)) {
			request.setStatus(statusMap.get(request.getStatusid()));
			requests.add(request);
		}
		return requests;

	}

	@Override
	public void request(int id, int statusid) {
		requestDAO.request(id, statusid);

	}

}
