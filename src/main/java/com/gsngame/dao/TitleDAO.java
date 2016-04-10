package com.gsngame.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gsngame.data.Title;

@Component
public interface TitleDAO {

	String TITLE_ALREADY_EXIST = "select count(*) from title where name=:name";
	
	String TITLE_ID_EXIST = "select count(*) from title where id=:id";

	String VALIDATE_REQUEST_STATUS = "select count(*) from requeststatus where status=:status";

	String UNSUBSCRIBED_TITLE_BY_USER = "select id,name,price,createdbyuserid from title t where t.id not in (select titleid from request where userid=1)";

	String REQUEST_STATUS = "select status from requeststatus";

	String TITLE_BY_USERId_AND_STATUS = "select id,name,price,createdbyuserid from title t "
			+ "where t.id in (select titleid from request where userid=:userid "
			+ "and statusid=(select id from requeststatus where status=:status))";

	String TITLES = "select id,name,price,createdbyuserid from title";
	
	public int title(Title title);

	public boolean checkTitleExist(String name);

	public boolean validateStatus(String status);

	public List<Title> unsubscribedTitleByUserId(int userid);

	public List<String> requestStatus();

	public List<Title> titleByUserIdandStatus(int userid, String status);

	public List<Title> titles();

	public boolean checkTitleIdExist(int id);

}
