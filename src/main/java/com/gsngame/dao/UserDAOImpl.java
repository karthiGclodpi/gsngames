package com.gsngame.dao;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import com.gsngame.data.Role;
import com.gsngame.data.User;

@Component
public class UserDAOImpl implements UserDAO {

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
	public boolean validateRole(int roleid) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("roleid", roleid);

		Integer value = 0;
		value = namedParameterJdbcTemplate.queryForObject(VALIDATE_ROLE,
				parameters, Integer.class);

		return value != null && value > 0;

	}

	@Override
	public int user(User user) {

		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		jdbcInsert.withTableName("user").usingGeneratedKeyColumns("id");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("username", user.getUsername());
		parameters.put("password", user.getPassword());
		parameters.put("name", user.getName());
		parameters.put("roleid", user.getRoleid());
		Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(
				parameters));
		return ((Number) key).intValue();

	}

	@Override
	public boolean checkUserExist(String username) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("username", username);

		Integer value = 0;
		value = namedParameterJdbcTemplate.queryForObject(USER_ALREADY_EXIST,
				parameters, Integer.class);

		return value != null && value > 0;
	}

	@Override
	public User getUserByUsername(String username) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("username", username);

		try {
			return (User) namedParameterJdbcTemplate.queryForObject(
					USER_BY_USERNAME, parameters,
					new BeanPropertyRowMapper<User>(User.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Role getRoleById(int roleid) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("roleid", roleid);

		return (Role) namedParameterJdbcTemplate.queryForObject(ROLE_BY_ID,
				parameters, new BeanPropertyRowMapper<Role>(Role.class));
	}

}
