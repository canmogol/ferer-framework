package com.fererlab.db;

import java.util.List;

/**
 * acm 11/28/12 9:25 AM
 */
public class DatabaseOperation {

    public static String SELECT = "SELECT";
    public static String INSERT = "INSERT";
    public static String UPDATE = "UPDATE";
    public static String DELETE = "DELETE";

    private String name;
    private String type;
    private String queryString;
    private List<String> queryParams;
    private List<String> returnParams;

    public DatabaseOperation() {
    }

    public DatabaseOperation(String name, String type, String queryString, List<String> queryParams, List<String> returnParams) {
        this.name = name;
        this.type = type;
        this.queryString = queryString;
        this.queryParams = queryParams;
        this.returnParams = returnParams;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public List<String> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(List<String> queryParams) {
        this.queryParams = queryParams;
    }

    public List<String> getReturnParams() {
        return returnParams;
    }

    public void setReturnParams(List<String> returnParams) {
        this.returnParams = returnParams;
    }
}
