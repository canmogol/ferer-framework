package com.fererlab.validation;


import java.util.ArrayList;
import java.util.List;

/**
 * acm 11/23/12 5:19 PM
 */
public class Validator {

    private List<String> validations = new ArrayList<String>();

    public Validator() {
    }

    public Validator(List<String> validations) {
        this.validations = validations;
    }

    public void validate() throws ValidationException {
        throw new ValidationException("Validation Exception");
    }

    public void addValidation(String validation){
        validations.add(validation);
    }

    public void setValidations(List<String> validations) {
        this.validations = validations;
    }

    public List<String> getValidations() {
        return validations;
    }
}
