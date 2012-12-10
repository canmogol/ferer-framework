package com.fererlab.node;

/**
 * acm 11/27/12 10:34 PM
 */
public class Tree {

    private Node currentNode;

    public Tree(Node currentNode) {
        this.currentNode = currentNode;
    }

    public Node getCurrentNode() {
        return currentNode;
    }
}
