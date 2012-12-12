package com.fererlab.test.flow;

import com.fererlab.converter.NoFormatter;
import com.fererlab.db.DatabaseConnection;
import com.fererlab.db.DatabaseOperation;
import com.fererlab.flow.FlowExecutor;
import com.fererlab.node.*;
import com.fererlab.validation.Validation;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * acm 11/27/12 9:24 PM
 */
public class FlowTester {

    private Document document;
    private SAXBuilder builder;
    private String xml = "" +
            "<root>" +
            " <http>" +
            "   <method>post</method>" +
            "   <uri>/login</uri>" +
            "   <post>" +
            "       <username>mitsubishi</username>" +
            "       <password>123@!Sushi</password>" +
            "   </post>" +
            "   <headers>" +
            "       <user-agent>Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405</user-agent>" +
            "   </headers>" +
            " </http>" +
            " <user id='1'>" +
            "   <username>aliosman</username>" +
            " </user>" +
            "</root>";

    public static void main(String[] args) {
        new FlowTester();
    }

    public FlowTester() {
        runTest();
    }

    private void runTest() {
        try {
            prepareForTest();
            flowTestNodes();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void prepareForTest() throws JDOMException, IOException {
        builder = new SAXBuilder();
        document = builder.build(new ByteArrayInputStream(xml.getBytes()));
    }

    private void flowTestNodes() throws Exception {

        // socket listen / HttpServlet -> HTTP parse key:value -> RequestParams -> param -> URI parse -> URI("/login")="flow43" -> flow manager (flow43)

        //                                                         [tx1|-[db]-|tx1]-(c)-(*)     JDOM Document = dataMap
        //                                                        /
        // JDOM Document -> Flow =   *-(c)-(v)-[tx0|-[db]-|tx0]-<c>-(c)-(*)

        // "/root/myVars/username = /root/POST[0]"
        // "/root/myVars/password = /root/uri[0]"

        // *
        Start start = new Start();

        // (c)
        Converter converter0 = new Converter(
                document
        );
        /*
        String                          ,  String                          , DateTimeToString{(...) -> (DD.MM.YYY)}
        "/root/users/user/register_date", "/root/myVars/user/register_date", Regexp String Manipulator Formatter.class, "DD.MM.YYYY"
         */
        converter0.addConversion(
                "/root/http/post/username",
                "/root/user/username",
                new NoFormatter(),
                "${empty.success.message}",
                "${conversion.error}"
        );
        converter0.addConversion(
                "/root/http/post/password",
                "/root/user/password",
                new NoFormatter(),
                "${empty.success.message}",
                "${conversion.error}"
        );
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
                new DatabaseOperation(  // "/root/users"
                        "select_user_operation",
                        DatabaseOperation.SELECT,
                        "select * from user where username=:username and password=:password",
                        Arrays.asList("/root/myVars/username", "/root/myVars/password"),
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
        Converter converter1 = new Converter(
                document
        );
        // /root/select_user_operation||users/register_date    java.sql.datetime->DD.MM.YYYY    /root/select_user_operation||users/register_date_day
        // /root/uri[0]  O->O  /root/myVars/username
        choice0.addChild(true, converter1);

        //  (*)
        End end0 = new End();
        converter1.addChild(end0);

        //  [tx|
        Transaction transaction1Begin = new Transaction(Transaction.BEGIN, "TRNX_OTHER_USER_SELECT");
        choice0.addChild(false, transaction1Begin);

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
        Converter converter2 = new Converter(
                document
        );
        transaction1End.addChild(converter2);

        //  (*)
        End end1 = new End();
        converter2.addChild(end1);

        // Sample Flow Tree
        Tree tree = new Tree(start);

        // create flow manager and run flow
        FlowExecutor flowExecutor = new FlowExecutor();

        // run flow through the tree
        flowExecutor.runFlow(tree);

        // this is the end node
        Node node = tree.getCurrentNode();
        if (node instanceof End) {
            // return the dataMap
            // if this flow was a sub-flow than continue to run the outer flow
        }
    }

}
