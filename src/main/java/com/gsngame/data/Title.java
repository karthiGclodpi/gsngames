package com.gsngame.data;

import javax.validation.constraints.NotNull;

public class Title {

	private int id;
	@NotNull
	private String name;
	@NotNull
	private Double price;
	private Integer createdbyuserid;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getCreatedbyuserid() {
		return createdbyuserid;
	}
	public void setCreatedbyuserid(Integer createdbyuserid) {
		this.createdbyuserid = createdbyuserid;
	}
	
	
}
