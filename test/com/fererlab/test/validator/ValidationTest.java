package com.fererlab.test.validator;

import com.fererlab.validation.Validator;

/**
 * acm 11/23/12 1:57 PM
 */
public class ValidationTest {

    public static void main(String[] args) {
        new ValidationTest();
    }

    public ValidationTest() {
        runTest();
    }

    private void runTest() {
        validationTest1();
    }

    private void validationTest1() {
        Validator validator = new Validator();
        validator.addValidation("");
    }
}
