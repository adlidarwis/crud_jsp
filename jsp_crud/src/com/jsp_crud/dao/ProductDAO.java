package com.jsp_crud.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jsp_crud.model.Product;

// Untuk operasi CRUD ke database
public class ProductDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/nabil_market?useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "password";
	
	private static final String INSERT_PRODUCT = "INSERT INTO products (product_name, price, stock, product_image) VALUES (?, ?, ?, ?);";
	private static final String SELECT_ALL_PRODUCTS = "SELECT * FROM products;";
	private static final String DELETE_PRODUCT_BY_ID = "DELETE FROM products WHERE ID = ?;";
	private static final String UPDATE_PRODUCT_BY_ID = "UPDATE products SET "
			+ "product_name = ?, "
			+ "price = ?,"
			+ "stock = ?,"
			+ "product_image = ?"
			+ "WHERE ID = ?;";
	private static final String SELECT_PRODUCT_BY_ID = "SELECT * FROM products WHERE id = ?;";
	
	public ProductDAO() {
	}
	
	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	
	public void insertProduct(Product product) throws SQLException {
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT)) {
			preparedStatement.setString(1, product.getProduct_name());
			preparedStatement.setInt(2, product.getPrice());
			preparedStatement.setInt(3, product.getStock());
			preparedStatement.setString(4, product.getProduct_image());
			
			System.out.println("===== SQL =====");
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}
	
	public List<Product> selectAllProducts () {
		List<Product> products = new ArrayList<>();
		
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCTS);) {
			
			System.out.println("===== SQL =====");
			System.out.println(preparedStatement);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("id");
				String productName = rs.getString("product_name");
				String productImage = rs.getString("product_image");
				int price = rs.getInt("price");
				int stock = rs.getInt("stock");
				
				products.add(new Product (id, productName, productImage, price, stock));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		
		return products;
	}
	
	public Product selectProduct (int id) {
		Product product = null;
		
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_ID);) {
			preparedStatement.setInt(1, id);
			
			System.out.println("===== SQL =====");
			System.out.println(preparedStatement);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				String productName = rs.getString("product_name");
				String productImage = rs.getString("product_image");
				int price = rs.getInt("price");
				int stock = rs.getInt("stock");
				
				product = new Product(id, productName, productImage, price, stock);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		
		return product;
	}
	
	public boolean deleteProduct (int id) throws SQLException {
		boolean rowDeleted;
		
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCT_BY_ID)) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		
		return rowDeleted;
	}
	
	public boolean updateProduct (Product product) throws SQLException {
		boolean rowUpdated;
		
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_PRODUCT_BY_ID);) {
			statement.setString(1, product.getProduct_name());
			statement.setInt(2, product.getPrice());
			statement.setInt(3, product.getStock());
			statement.setString(4, product.getProduct_image());
			statement.setInt(5, product.getId());
			
			rowUpdated = statement.executeUpdate() > 0;
		}
		
		return rowUpdated;
	}
	
	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}
}
