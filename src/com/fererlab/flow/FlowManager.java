package com.fererlab.flow;

import com.fererlab.node.Converter;
import com.fererlab.node.Node;
import com.fererlab.node.Start;
import com.fererlab.node.Tree;
import com.fererlab.validation.Validator;

import java.util.logging.Logger;

/**
 * acm 11/27/12 9:41 PM
 */
public class FlowManager {

    private Logger logger = Logger.getLogger(getClass().getName());

    public void runFlow(Tree tree) throws Exception {
        tree.getCurrentNode(); // this should be the Start
        if (tree.getCurrentNode() instanceof Start) {
            startRunning(tree.getCurrentNode());
        } else {
            throw new Exception("current node should be Start node, tree.getCurrentNode(): " + tree.getCurrentNode());
        }
    }

    private void startRunning(Node startNode) throws Exception {
        handleNode(startNode);
    }

    private void handleNode(Node node) {
        logger.info("there is no method implemented for this method, node: " + node);
    }

    private void handleNode(Start start) {
        handleNode(start.getChildren().get(0));
    }

    private void handleNode(Converter converter) {
        //Map<> converter.getConversionMap();
    }

    private void handleNode(Validator validator) {
    }

    // other implementations below
    // ...


}
