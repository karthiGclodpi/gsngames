package com.gsngame.business;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gsngame.data.Title;

@Component
public interface TitleBO {

	public Title title(Title title);

	public boolean validateStatus(String status);

	public List<Title> titleByUserIdandStatus(int parseInt, String status);

	public List<Title> unsubscribedTitleByUserId(int parseInt);

	public String validStatus();

	public List<Title> titles();

	public boolean checkTitleIdExist(int id);

}
