package com.fererlab.converter;

/**
 * acm 12/3/12 9:44 AM
 */
public class Conversion {

    private String sourcePath;
    private String destinationPath;
    private Formatter formatter;
    private String successMessage;
    private String errorMessage;

    public Conversion(String sourcePath, String destinationPath, Formatter formatter, String successMessage, String errorMessage) {
        this.sourcePath = sourcePath;
        this.destinationPath = destinationPath;
        this.formatter = formatter;
        this.successMessage = successMessage;
        this.errorMessage = errorMessage;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public String getDestinationPath() {
        return destinationPath;
    }

    public Formatter getFormatter() {
        return formatter;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
