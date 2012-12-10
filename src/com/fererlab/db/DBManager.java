package com.fererlab.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DBManager {

	private String masterDBAlias;
	private DBConnection dbConnection;
	private Connection masterConnection;
	private Map<String, Connection> shardConnections;
	
	private DBManager(String masterDbName, String connectionUrl){
		
		masterDBAlias = masterDbName;
		
		this.dbConnection = new DBSimpleConnection(connectionUrl);
		
		this.shardConnections = new HashMap<String, Connection>();
	}
	
	public static DBManager getDBManager(String masterDbName, String connectionUrl){
		return new DBManager(masterDbName, connectionUrl);
	}
	
	public Connection getConnection(){
		
		if(this.masterConnection != null){
			return this.masterConnection;
		}
		
		Connection conn = this.dbConnection.getConnection(masterDBAlias);
		this.masterConnection = conn;

		return conn;
	}
	
	public Connection getConnection(String dbAlias){
		
		if(this.shardConnections.get(dbAlias) != null){
			return this.shardConnections.get(dbAlias);
		}
		
		Connection conn = this.dbConnection.getConnection(dbAlias);
		this.shardConnections.put(dbAlias, conn);

		return conn;
	}
	
	public void transactionBegin() throws SQLException{
		getConnection().setAutoCommit(false);
	}
	
	public void transactionCommit() throws SQLException{
		getConnection().commit();
	}
	
	public void transactionRollback() throws SQLException{
		getConnection().rollback();
	}
	
	public void transactionEnd() throws SQLException{
		getConnection().setAutoCommit(true);
	}
	
	public void closeConnection(){
		dbConnection.closeConnection(masterConnection);
	}
	
	public void closeConnection(String alias){
		dbConnection.closeConnection(shardConnections.get(alias));
	}
	
	public void closeConnections(){
		
		dbConnection.closeConnection(masterConnection);
		
		for(Connection connection : shardConnections.values()){
			dbConnection.closeConnection(connection);
		}
		
	}
	
}
