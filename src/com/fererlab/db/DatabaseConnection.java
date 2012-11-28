package com.fererlab.db;

/**
 * acm 11/28/12 9:31 AM
 */
public class DatabaseConnection {

    private String jdbcString;
    private String driverClassName;
    private String username;
    private String password;

    public DatabaseConnection() {
    }

    public DatabaseConnection(String jdbcString, String driverClassName, String username, String password) {
        this.jdbcString = jdbcString;
        this.driverClassName = driverClassName;
        this.username = username;
        this.password = password;
    }

    public String getJdbcString() {
        return jdbcString;
    }

    public void setJdbcString(String jdbcString) {
        this.jdbcString = jdbcString;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "DatabaseConnection{" +
                "jdbcString='" + jdbcString + '\'' +
                ", driverClassName='" + driverClassName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
