package com.fererlab.converter;

/**
 * acm 12/3/12 9:41 AM
 */
public class NoFormatter implements Formatter {
    @Override
    public String format(String s) {
        return s;
    }
}
