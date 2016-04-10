package com.gsngame.business;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gsngame.dao.TitleDAO;
import com.gsngame.data.Title;
import com.gsngame.exception.GSNConflictException;

@Component
public class TitleBOImpl implements TitleBO {

	@Autowired
	private TitleDAO titleDAO;

	@Override
	public Title title(Title title) {
		if(!titleDAO.checkTitleExist(title.getName()))
			title.setId(titleDAO.title(title));
		else
			throw new GSNConflictException("Title already exist");
		return title;
	}

	@Override
	public boolean validateStatus(String status) {
		
		return titleDAO.validateStatus(status);
	}

	@Override
	public List<Title> titleByUserIdandStatus(int userid, String status) {
		
		return titleDAO.titleByUserIdandStatus(userid,status);
	}

	@Override
	public List<Title> unsubscribedTitleByUserId(int userid) {
		
		return titleDAO.unsubscribedTitleByUserId(userid);
	}

	@Override
	public String validStatus() {
		List<String> status=titleDAO.requestStatus();
		return Arrays.toString(status.toArray());
	}

	@Override
	public List<Title> titles() {
		return titleDAO.titles();
	}

	@Override
	public boolean checkTitleIdExist(int id) {
		
		return titleDAO.checkTitleIdExist(id);
	}

}
