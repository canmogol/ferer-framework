package com.fererlab.node;

/**
 * acm 11/27/12 9:59 PM
 */
public class Transaction extends Node {

    public static String BEGIN = "BEGIN";
    public static String COMMIT = "COMMIT";
    public static String ROLLBACK = "ROLLBACK";
    public static String NO_TRANSACTION = "NO_TRANSACTION";
    private String type = NO_TRANSACTION;
    private String transactionId;

    public Transaction(String type, String transactionId) {
        this.type = type;
        this.transactionId = transactionId;
    }

    public String getType() {
        return type;
    }

    public String getTransactionId() {
        return transactionId;
    }

    @Override
    public void execute() throws Exception {
        throw new Exception("unimplemented method execute in class: " + getClass().getName());
    }

}
