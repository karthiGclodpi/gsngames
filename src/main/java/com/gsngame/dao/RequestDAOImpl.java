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

import com.gsngame.data.Request;
import com.gsngame.data.RequestStatus;


@Component
public class RequestDAOImpl implements RequestDAO {

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
	public int request(Request request) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		jdbcInsert.withTableName("request").usingGeneratedKeyColumns("id");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userid", request.getUserid());
		parameters.put("titleid", request.getTitleid());
		parameters
				.put("statusid", requestStatusIdByStatus(request.getStatus()));
		Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(
				parameters));
		return ((Number) key).intValue();
	}

	private int requestStatusIdByStatus(String status) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("status", status);

		return namedParameterJdbcTemplate.queryForObject(
				REQUEST_STATUS_BY_STATUS, parameters, Integer.class);
	}

	@Override
	public String statusByRequestId(int id) {

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", id);

		return namedParameterJdbcTemplate.queryForObject(
				REQUEST_STATUS_BY_REQUEST_ID, parameters, String.class);
	}

	@Override
	public boolean checkRequestExist(int userid, int titleid) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userid", userid);
		parameters.put("titleid", titleid);

		Integer value = 0;
		value = namedParameterJdbcTemplate.queryForObject(CHECK_REQUEST_EXIST,
				parameters, Integer.class);

		return value != null && value > 0;
	}

	@Override
	public List<Request> request() {
		return namedParameterJdbcTemplate.query(REQUEST,
				new BeanPropertyRowMapper<Request>(Request.class));
	}

	@Override
	public List<RequestStatus> requestStatus() {
		return namedParameterJdbcTemplate.query(REQUEST_STATUS,
				new BeanPropertyRowMapper<RequestStatus>(RequestStatus.class));
	}

	@Override
	public List<Request> request(String status) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("statusid", requestStatusIdByStatus(status));
		return namedParameterJdbcTemplate.query(REQUEST_STATUS_ID, parameters,
				new BeanPropertyRowMapper<Request>(Request.class));
	}

	@Override
	public List<Request> request(int requestid) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", requestid);
		return namedParameterJdbcTemplate.query(REQUEST_BY_ID, parameters,
				new BeanPropertyRowMapper<Request>(Request.class));
	}

	@Override
	public void request(int id, int statusid) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", id);
		parameters.put("statusid", statusid);
		namedParameterJdbcTemplate.update(UPDATE_REQUEST, parameters);
		
	}

}
