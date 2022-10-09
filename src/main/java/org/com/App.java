package org.com;

import org.com.node.Node;
import org.com.util.MDReader;

import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App  {
    public static void main( String[] args ) {

            System.out.println( "Hello World!" );
            MDRender render = new MDRender("src/main/java/org/com/link_md_file.md");
            List<Node> nodes = render.findChildren("this is a p tag [Mailchimp](www.mailchimp.com) this is another p tag");
            System.out.println("Number of lines is " + render.data.size());
            System.out.println("Number of nodes is " + nodes.size());
    }
}
