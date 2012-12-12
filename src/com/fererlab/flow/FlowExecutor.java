package com.fererlab.flow;

import com.fererlab.node.Converter;
import com.fererlab.node.Node;
import com.fererlab.node.Start;
import com.fererlab.node.Tree;
import com.fererlab.validation.Validator;

import java.lang.reflect.Method;
import java.util.logging.Logger;

/**
 * acm 11/27/12 9:41 PM
 */
public class FlowExecutor {

    private Logger logger = Logger.getLogger(getClass().getName());

    public void runFlow(Tree tree) throws Exception {
        tree.getCurrentNode(); // this should be the Start
        if (tree.getCurrentNode() instanceof Start) {
            handleNode(tree.getCurrentNode());
        } else {
            throw new Exception("current node should be Start node, tree.getCurrentNode(): " + tree.getCurrentNode());
        }
    }

    private void handleNode(Node node) {
        try {
            Method method = this.getClass().getDeclaredMethod("handleNode", node.getClass());
            method.invoke(this, node);
        } catch (Exception e) {
            // TODO try to store this error, return failed flow message
            e.printStackTrace();
        }
    }

    private void handleNode(Start start) {
        System.out.println("start: " + start);
        Node node = start.getChildren().get(0);
        handleNode(node);
    }

    private void handleNode(Converter converter) throws Exception {
        System.out.println("converter: " + converter);
        converter.execute();
    }

    private void handleNode(Validator validator) {
        System.out.println("validator: " + validator);
    }

    // other implementations below
    // ...


}
