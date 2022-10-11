package org.com;

import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.com.node.*;
import org.com.util.MDReader;
import org.com.util.Render;


public class MDRender implements Render {
    private List<Node> mdNodes = new ArrayList<>();
    private List<String> data;
    private MDReader reader;
    private static final int COUNT_HEADER_MAX = 6;
    //Pattern found using StackOverflow: https://stackoverflow.com/a/70326197
    private static final String REGEX_LINK = "\\[([^\\]]+)\\]\\(([^)]+)\\)";
    private static final String REGEX_HEADER = "#+";
    private static final Pattern PATTERN_LINK = Pattern.compile(REGEX_LINK);
    private static final Pattern PATTERN_HEADER = Pattern.compile(REGEX_HEADER);
    public MDRender(String path) {
        reader = new MDReader(path);
        data = reader.getLines();
    }

    /**
     * Method that creates a list of node objects from the markdown
     * data that gets read from the input md file. Checks for specific
     * conditions when creating parent and child nodes.
     *
     * @return List of Nodes
     */
    @Override
    public List<Node> render() {


        //Check if header, paragraph or text;
        for(String line : data) {
            line.trim();
            Node current = checkLine(line);
            int contentStart = 0;
            if(current == null) {
                current = generateParagraphNode();
            }
            //If null or link, add node
            if(current.getType() == NodeType.NULL || current.getType() == NodeType.LINK) {
                mdNodes.add(current);
                continue;
            } else if(current.getType() == NodeType.HEADER) {
                contentStart = current.getLevel() + 1;
            }


            List<Node> children = findChildren(line.substring(contentStart));
            for(Node child : children) {
                current.addChild(child);
            }
            mdNodes.add(current);

        }

        mdNodes = linkParagraphNodes();
        return mdNodes;


    }

    /**
     * Method to go through the rendered nodes and link consecutive
     * text nodes to each other for proper paragraph html generation.
     *
     * @return List of nodes with all consecutive paragraph nodes linked
     */
    private List<Node> linkParagraphNodes() {
        int currentIndex = 0;
        Node current = null;
        List<Node> newNodes = new ArrayList<>();
        //Loop over all nodes and combine consecutive text nodes
        while(currentIndex < mdNodes.size()) {
            if(mdNodes.get(currentIndex).getType() != NodeType.PARAGRAPH) {
                if (current != null) {
                    newNodes.add(current);
                    current = null;
                }
                newNodes.add(mdNodes.get(currentIndex));
            } else {
                if(current != null) {
                    current.getChildren().addAll(mdNodes.get(currentIndex).getChildren());

                } else {
                    current = mdNodes.get(currentIndex);
                }
            }
            currentIndex++;

        }

        if(current != null) {
            newNodes.add(current);
        }
        return newNodes;
    }

    /**
     * Method that breaks a line into link and text nodes
     *
     * @param line
     * @return List of Node
     *
     * Method that looks for a pattern match for a link element and renders a
     */
    public List<Node> findChildren(String line) {
        Matcher m = PATTERN_LINK.matcher(line);
        List<Node> nodeList = new ArrayList<>();
        int startIndex = 0;
        while(m.find()) {
            //If we found some paragraph text before the link match, create a Text Node
            if(m.start() - startIndex > 0) {
                TextNode node = new TextNode(NodeType.TEXT, line.substring(startIndex, m.start()));
                nodeList.add(node);
            }


            Node node = generateLinkNode(m.group(2), m.group(1));

            nodeList.add(node);
            startIndex = m.end();
        }

        //If after matching a link some text remains, create another Text Node
        if(startIndex < line.length()) {
            nodeList.add(new TextNode(NodeType.TEXT, line.substring(startIndex, line.length())));
        }
        return nodeList;
    }

    /**
     * Checks a line for the 3 possible parent node start cases that
     * aren't null node (br, h, a).
     *
     * @param line
     * @return Node
     */
    public Node checkLine(String line) {
        Node node = checkBlank(line);
        if(node != null) {
            return node;
        }
        node = checkHeader(line);
        if(node != null) {
            return node;
        }

        node = checkLink(line);
        if(node != null) {
            return node;
        }
        return null;
    }

    /**
     *Checks if the line starts with a link pattern and returns
     * a link node
     *
     * @param line
     * @return Link node or null node
     */
    private Node checkLink(String line) {
        Matcher m = PATTERN_LINK.matcher(line);
        int numFound = 0;
        MatchResult res = null;
        while(m.find()) {

            numFound++;
            res = m.toMatchResult();

        }
        if(numFound == 1 && res.start() == 0 && res.end() == line.length()) {
            return generateLinkNode(res.group(2), res.group(1));
        }
        return null;
    }

    /**
     * Checks if the line starts with a header pattern and returns
     * a header node at the appropriate level if found.
     *
     * @param line
     * @return Node of type Header
     */
    private Node checkHeader(String line) {

        Matcher m = PATTERN_HEADER.matcher(line);

        while(m.find()) {
            if(m.start() == 0 && m.end() - m.start() <= COUNT_HEADER_MAX) {
                return new HeaderNode(NodeType.HEADER, m.end() - m.start());
            }

        }

        return null;

    }

    /**
     * Checks if a line is empty and if it
     * is, creates and returns a null node
     *
     * @param line
     * @return Null node
     */
    private NullNode checkBlank(String line) {
        if(line.trim().length() == 0) {
            return new NullNode(NodeType.NULL);
        }
        return null;
    }

    /**
     * Creates a paragraph node
     *
     * @return Node of type Paragraph
     */
    private Node generateParagraphNode() {
        return new ParagraphNode(NodeType.PARAGRAPH);
    }

    /**
     * Generates a link node, which is a link node
     * that has a child node with the link text
     *
     * @param link
     * @param text
     * @return Node of type Link
     */
    private LinkNode generateLinkNode(String link, String text) {
        LinkNode node = new LinkNode(NodeType.LINK, link);
        TextNode linkText = new TextNode(NodeType.TEXT, text);
        node.addChild(linkText);
        return node;

    }

    public List<Node> getMdNodes() {
        return this.mdNodes;
    }

}
