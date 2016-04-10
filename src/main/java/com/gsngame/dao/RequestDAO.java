package com.gsngame.dao;



import java.util.List;

import org.springframework.stereotype.Component;

import com.gsngame.data.Request;
import com.gsngame.data.RequestStatus;


@Component
public interface RequestDAO {

	String REQUEST_STATUS_BY_REQUEST_ID="select status from requeststatus where id=(select statusid from request where id=:id)";
	
	String CHECK_REQUEST_EXIST="select count(*) from request where userid=:userid and titleid=:titleid";
	
	String REQUEST_STATUS_BY_STATUS="select id from requeststatus where status=:status";
	
	String REQUEST="select id,userid,titleid,statusid from request";
	
	String REQUEST_STATUS_ID="select id,userid,titleid,statusid from request where statusid=:statusid";
	
	String REQUEST_BY_STATUS="select id,userid,titleid,statusid from request";
	
	String REQUEST_BY_ID=REQUEST_BY_STATUS +" where id=:id";
	
	String REQUEST_STATUS="select id,status from requeststatus";
	
	String UPDATE_REQUEST="update request set statusid=:statusid where id=:id";
	
	public int request(Request request);

	public String statusByRequestId(int id);

	public boolean checkRequestExist(int userid, int titleid);

	public List<Request> request();

	public List<RequestStatus> requestStatus();

	public List<Request> request(String status);

	public List<Request> request(int requestid);

	public void request(int id, int statusid);
	
}
