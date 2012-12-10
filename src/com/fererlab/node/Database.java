package com.fererlab.node;

import com.fererlab.db.DatabaseConnection;
import com.fererlab.db.DatabaseOperation;

/**
 * acm 11/27/12 10:16 PM
 */
public class Database extends Node {

    private DatabaseConnection databaseConnection;
    private DatabaseOperation databaseOperation;

    public Database(DatabaseConnection databaseConnection, DatabaseOperation databaseOperation) {
        this.databaseConnection = databaseConnection;
        this.databaseOperation = databaseOperation;
    }

    public DatabaseConnection getDatabaseConnection() {
        return databaseConnection;
    }

    public DatabaseOperation getDatabaseOperation() {
        return databaseOperation;
    }

    @Override
    public void execute() throws Exception {
        throw new Exception("unimplemented method execute in class: " + getClass().getName());
    }

}
