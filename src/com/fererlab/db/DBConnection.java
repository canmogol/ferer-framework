package com.fererlab.db;

import java.sql.Connection;

public interface DBConnection {
	
	public Connection getConnection(String alias);
	
	public void closeConnection(Connection connection);

}
