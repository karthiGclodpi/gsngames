package com.gsngame.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import com.gsngame.data.Title;

@Component
public class TitleDAOImpl implements TitleDAO {

	private DataSource dataSource;

	private JdbcTemplate jdbcTemplate;

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public DataSource getDataSource() {
		return dataSource;
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);

	}

	@Override
	public int title(Title title) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		jdbcInsert.withTableName("title").usingGeneratedKeyColumns("id");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("name", title.getName());
		parameters.put("price", title.getPrice());
		parameters.put("createdbyuserid", title.getCreatedbyuserid());
		Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(
				parameters));
		return ((Number) key).intValue();

	}

	@Override
	public boolean checkTitleExist(String name) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("name", name);

		Integer value = 0;
		value = namedParameterJdbcTemplate.queryForObject(TITLE_ALREADY_EXIST,
				parameters, Integer.class);

		return value != null && value > 0;
	}

	@Override
	public boolean validateStatus(String status) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("status", status);

		Integer value = 0;
		value = namedParameterJdbcTemplate.queryForObject(
				VALIDATE_REQUEST_STATUS, parameters, Integer.class);

		return value != null && value > 0;
	}

	@Override
	public List<Title> unsubscribedTitleByUserId(int userid) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userid", userid);

		return namedParameterJdbcTemplate.query(UNSUBSCRIBED_TITLE_BY_USER,
				parameters, new BeanPropertyRowMapper<Title>(Title.class));

	}

	@Override
	public List<String> requestStatus() {
		return (List<String>) jdbcTemplate.queryForList(REQUEST_STATUS,
				String.class);
	}

	@Override
	public List<Title> titleByUserIdandStatus(int userid, String status) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userid", userid);
		parameters.put("status", status);

		return namedParameterJdbcTemplate.query(TITLE_BY_USERId_AND_STATUS,
				parameters, new BeanPropertyRowMapper<Title>(Title.class));
	}

	@Override
	public List<Title> titles() {
		return namedParameterJdbcTemplate.query(TITLES,
				new BeanPropertyRowMapper<Title>(Title.class));
	}

	@Override
	public boolean checkTitleIdExist(int id) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", id);

		Integer value = 0;
		value = namedParameterJdbcTemplate.queryForObject(TITLE_ID_EXIST,
				parameters, Integer.class);

		return value != null && value > 0;
	}

}
