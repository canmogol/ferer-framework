package com.fererlab.test.flow;

import com.fererlab.db.DatabaseConnection;
import com.fererlab.db.DatabaseOperation;
import com.fererlab.flow.FlowManager;
import com.fererlab.node.*;
import com.fererlab.validation.Validation;

import java.util.Arrays;

/**
 * acm 11/27/12 9:24 PM
 */
public class FlowTester {

    public static void main(String[] args) {
        new FlowTester();
    }

    public FlowTester() {
        runTest();
    }

    private void runTest() {
        try {
            flowTestNodes();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void flowTestNodes() throws Exception {
        //                                           [tx1|-[db]-|tx1]-(c)-(*)
        //                                          /
        //  Sample Flow =   *-(c)-(v)-[tx0|-[db]-|tx0]-<c>-(c)-(*)

        // *
        Start start = new Start();

        // (c)
        Converter converter0 = new Converter();
        start.addChild(converter0);

        // (v)
        Validator validator0 = new Validator(
                Arrays.asList(
                        new Validation("/root/user/username='acm'", "Only ACM can login!"),
                        new Validation("/root/user/password='123456'", "${wrong.password}")
                )
        );
        converter0.addChild(validator0);

        //  [tx|
        Transaction transaction0Begin = new Transaction(Transaction.BEGIN, "TRNX_USER_SELECT");
        validator0.addChild(transaction0Begin);

        //  [db]
        Database database0 = new Database(
                new DatabaseConnection(
                        "jdbc:postgresql://127.0.0.1/campus",
                        "org.postgresql.Driver",
                        "postgres",
                        "123456"
                ),
                new DatabaseOperation(
                        "select_user_operation",
                        DatabaseOperation.SELECT,
                        "select * from user where username=:username and password=:password",
                        Arrays.asList("username", "password"),
                        Arrays.asList("id", "name", "surname", "address", "phone", "username", "password", "register_date")
                )
        );
        transaction0Begin.addChild(database0);

        //  |tx]
        Transaction transaction0End = new Transaction(Transaction.COMMIT, "TRNX_USER_SELECT");
        database0.addChild(transaction0End);

        //  <c>
        Choice choice0 = new Choice();
        transaction0End.addChild(choice0);

        //  (c)
        Converter converter1 = new Converter();
        choice0.addChild(true, converter1);

        //  (*)
        End end0 = new End();
        converter1.addChild(end0);

        //  [tx|
        Transaction transaction1Begin = new Transaction(Transaction.BEGIN, "TRNX_OTHER_USER_SELECT");
        choice0.addChild(true, transaction1Begin);

        //  [db]
        Database database1 = new Database(
                new DatabaseConnection(
                        "jdbc:postgresql://172.146.21.234/campus19",
                        "org.postgresql.Driver",
                        "postgres",
                        "123456"
                ),
                new DatabaseOperation(
                        "select_other_user_operation",
                        DatabaseOperation.SELECT,
                        "select * from other_user where username=:username and password=:password",
                        Arrays.asList("username", "password"),
                        Arrays.asList("id", "name", "surname", "address", "phone", "username", "password", "register_date")
                )
        );
        transaction1Begin.addChild(database1);

        //  |tx]
        Transaction transaction1End = new Transaction(Transaction.COMMIT, "TRNX_OTHER_USER_SELECT");
        database1.addChild(transaction1End);

        //  (c)
        Converter converter2 = new Converter();
        transaction1End.addChild(converter2);

        //  (*)
        End end1 = new End();
        converter2.addChild(end1);

        // Sample Flow Tree
        Tree tree = new Tree(start);

        // create flow manager and run flow
        FlowManager flowManager = new FlowManager();

        // run flow through the tree
        flowManager.runFlow(tree);

        // this is the end node
        Node node = tree.getCurrentNode();
        if (node instanceof End) {
            // return the dataMap
            // if this flow was a sub-flow than continue to run the outer flow
        }
    }

}
