package com.fererlab.db;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.fererlab.util.PropertyUtil;

public class DBSimpleConnection  implements DBConnection{

	private String connectionUrl;
	
	static{
		
		try {
			
			Class<?> driverClass = Class.forName(PropertyUtil.getProperty("db-driver"));
			
			Driver driver = (Driver) driverClass.newInstance();
			
			DriverManager.registerDriver(driver);
			
		} catch (ClassNotFoundException e1) {
			// TODO : handle or log exception
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO : handle or log exception
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO : handle or log exception
			e1.printStackTrace();
		} catch (SQLException e) {
			// TODO : handle or log exception
			throw new RuntimeException("driver error : ", e);
		}
	}
	
	public DBSimpleConnection(String connectionUrl) {
		this.connectionUrl = connectionUrl;
	}

	@Override
	public Connection getConnection(String dbName) {

		Connection connection = null;
		try {
			connection = DriverManager.getConnection(this.connectionUrl);
		} catch (SQLException e) {
			// TODO : handle or log exception
			throw new RuntimeException(e);
		}
		
		return connection;
	}

	@Override
	public void closeConnection(Connection connection) {

		if(connection != null){
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO : handle or log exception
				throw new RuntimeException(e);
			}finally{
				connection = null;
			}
		}
	}
}