package org.com;


import org.com.node.Node;
import org.com.node.NodeType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

public class HTMLGenerator {
    private List<Node> nodes;
    private Document doc = Jsoup.parse("<html></html>");
    private static final Map<Integer, String> MAP_HEADER = new HashMap<Integer, String>(){{
       put(1, "h1");
       put(2, "h2");
       put(3, "h3");
       put(4, "h4");
       put(5, "h5");
       put(6, "h6");
    }};
    public HTMLGenerator(List<Node> nodes){
        this.nodes = nodes;
    }

    /**
     * Runs html generation and writes to output file
     */

    /**
     * Runs html generation and writes to output file
     *
     * @param fileName
     */
    public void testHTML(String fileName) {

        try{
            final File f = new File(fileName);
            generateHTML(doc, nodes);
            FileUtils.writeStringToFile(f, doc.outerHtml());
        } catch (Exception e) {
            System.out.println("There was an exception that occurred");
        }

    }

    /**
     * Method that takes in a doc and list of nodes and generates
     * HTML utilizing the recursive node conversion function;
     *
     * @param doc
     * @param nodes
     */
    private void generateHTML(Document doc, List<Node> nodes) {
        for (Node node : nodes) {
            Element e = convertNodeToHTML(node, null);
            doc.body().appendChild(e);
        }

        System.out.println(doc.body());

    }

    /**
     * Method that takes in a root node and converts that node and its
     * children to Jsoup Elements. Text nodes are appended within each
     * specific node case based on conditions specific to that case.
     *
     * @param node
     * @param root
     * @return root Element
     */
    private Element convertNodeToHTML(Node node, Element root) {
        if (node.getType() == NodeType.HEADER) {
            root = new Element(MAP_HEADER.get(node.getLevel()));
            //We know header is just one line of data
            if(node.getChildren().size() > 0 && node.getChildren().get(0).getType() == NodeType.TEXT) {
                root.text(node.getChildren().get(0).getText());
            }
        } else if(node.getType() == NodeType.PARAGRAPH) {
            root = new Element("p");
            List<Node> children = node.getChildren();
            int size = children.size();
            //Paragraph can have multiple consecutive text nodes, so check immediately here
            if(children.size() > 0 && children.get(0).getType() == NodeType.TEXT) {
                int i = 0;
                StringBuilder sb = new StringBuilder();

                while(i < size && (children.get(i).getType() == NodeType.TEXT)) {
                    sb.append(children.get(i).getText()).append(" ");
                    i++;
                }

                root.text(sb.toString());
            }

        } else if(node.getType() == NodeType.LINK) {
            root = new Element("a");
            String name = "";
            //Next text node will be the text associated with the link
            if (node.getChildren().size() > 0 && node.getChildren().get(0).getType() == NodeType.TEXT) {
                name = node.getChildren().get(0).getText();
            }
            StringBuilder sb = new StringBuilder();
            root.attr("href", node.getText());
            root.text(name);
        } else {
            return new Element("br");
        }

        if(node.getChildren().size() != 0) {
            List<Node> children = node.getChildren();
            for(int i = 0; i < children.size(); i++) {
                //Check within the recursive function for all the cases where we add text
                //to nodes, so only recurse if the node is a fresh element type other than text
                if(children.get(i).getType() != NodeType.TEXT) {
                    root.appendChild(convertNodeToHTML(children.get(i), null));
                }

                //Edge case where a paragraph contains a link and more text at the end
                if(i > 0 && node.getType() == NodeType.PARAGRAPH &&
                        children.get(i).getType() == NodeType.TEXT &&
                        children.get(i-1).getType() == NodeType.LINK) {
                    root.appendText(children.get(i).getText());
                    i++;
                    //Since we found a text node after link start, check for more consecutive text nodes
                    while(i < children.size() && children.get(i).getType() == NodeType.TEXT) {
                        StringBuilder sb = new StringBuilder();
                        root.appendText(sb.append(" ").append(children.get(i).getText()).toString());
                        i++;
                    }
                }
            }
        }

        return root;

    }
}
