package com.fererlab.node;

import java.util.HashMap;
import java.util.Map;

/**
 * acm 11/27/12 10:16 PM
 */
public class Choice extends Node {

    private Map<Object, Node> conditionResultNodeMap = new HashMap<Object, Node>();

    public void addChild(Object conditionResult, Node node) {
        conditionResultNodeMap.put(conditionResult, node);
    }

    @Override
    public void execute() throws Exception {
        throw new Exception("unimplemented method execute in class: " + getClass().getName());
    }

}
