package org.com;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.com.node.Node;
import org.com.node.NodeType;
import org.junit.Test;

import java.util.List;

/**
 * Unit test for simple App.
 */
public class MDRenderTest
{
    /**
     * Rigorous Test :-)
     */

    private String linkMD = "[Mailchimp](www.mailchimp.com)";
    private String headerMD = "# This is a header";
    private String emptyMD = " ";
    private String paragraphMD = "This is a paragraph.";
    private String findChildrenMD = "This is a paragraph [This is inline link](www.mailchimp.com).";
    private MDRender render = new MDRender("src/md_test_1.md");


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
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
}
