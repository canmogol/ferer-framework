package com.fererlab.node;

import java.util.ArrayList;
import java.util.List;

/**
 * acm 11/27/12 9:56 PM
 */
public abstract class Node {

    private Node parent;
    private List<Node> children = new ArrayList<Node>();

    protected Node() {
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public void addChild(Node child) {
        this.children.add(child);
    }

    public abstract void execute() throws Exception;

}