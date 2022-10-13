package org.com;

import static org.junit.Assert.assertEquals;

import org.com.node.Node;
import org.com.node.NodeType;
import org.junit.Test;

import java.util.List;

/**
 * Unit test for simple App.
 */
public class MDRenderTest
{


    private String linkMD = "[Mailchimp](www.mailchimp.com)";
    private String headerMD = "# This is a header";
    private String emptyMD = " ";
    private String findChildrenMD = "This is a paragraph [This is an inline link](www.mailchimp.com).";
    private MDRender render = new MDRender("src/linkTest.md");
    private int NUM_NODES_LINK_TEST = 13;


    @Test
    public void testCheckLine() {

        Node link = render.checkLine(linkMD);
        Node header = render.checkLine(headerMD);
        Node empty = render.checkLine(emptyMD);
        assertEquals(link.getType(), NodeType.LINK);
        assertEquals(header.getType(), NodeType.HEADER);
        assertEquals(empty.getType(), NodeType.NULL);

    }

    @Test
    public void testFindChildren() {

       List<Node> nodes = render.findChildren(findChildrenMD);
       assertEquals(nodes.size(), 3);

    }

    @Test
    public void testRender() {
        render.render();
        List<Node> nodes = render.getMdNodes();
        Node paragraph = nodes.get(0);
        System.out.println("Size is: " + paragraph.getChildren().size());
        assertEquals(paragraph.getType(), NodeType.PARAGRAPH);
        assertEquals(paragraph.getChildren().get(0).getType(), NodeType.LINK);
        assertEquals(paragraph.getChildren().get(1).getType(), NodeType.TEXT);
        assertEquals(paragraph.getChildren().get(2).getType(), NodeType.LINK);
        assertEquals(nodes.size(), NUM_NODES_LINK_TEST);
    }

}
