package com.fererlab.node;

import com.fererlab.validation.Validation;

import java.util.ArrayList;
import java.util.List;

/**
 * acm 11/27/12 9:59 PM
 */
public class Validator extends Node {

    private List<Validation> validations = new ArrayList<>();

    public Validator(List<Validation> validations) {
        this.validations = validations;
    }

    @Override
    public void execute() throws Exception {
        throw new Exception("unimplemented method execute in class: " + getClass().getName());
    }

}
