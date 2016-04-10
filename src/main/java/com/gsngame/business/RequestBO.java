package com.gsngame.business;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gsngame.data.Request;

@Component
public interface RequestBO {

	public Request request(Request request);

	public List<Request> request();

	public List<Request> request(String status);

	public List<Request> request(int requestid);

	public void request(int id, int statusid);

}
