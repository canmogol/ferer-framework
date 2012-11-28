package com.fererlab.validation;

/**
 * acm 11/28/12 9:17 AM
 */
public class Validation {

    private String expression;
    private String message;

    public Validation() {
    }

    public Validation(String expression, String message) {
        this.expression = expression;
        this.message = message;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Validation{" +
                "expression='" + expression + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
