package com.jsp_crud.model;

public class Product {
	private int id;
	private String product_name;
	private String product_image;
	private int price;
	private int stock;
	
	public Product(int id, String product_name, String product_image, int price, int stock) {
		super();
		this.id = id;
		this.product_name = product_name;
		this.product_image = product_image;
		this.price = price;
		this.stock = stock;
	}
	
	public Product(String product_name, String product_image, int price, int stock) {
		super();
		this.product_name = product_name;
		this.product_image = product_image;
		this.price = price;
		this.stock = stock;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getProduct_image() {
		return product_image;
	}
	public void setProduct_image(String product_image) {
		this.product_image = product_image;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	
}
