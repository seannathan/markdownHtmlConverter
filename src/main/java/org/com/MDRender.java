package org.com;

import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.com.node.*;
import org.com.util.MDReader;
import org.com.util.Render;


public class MDRender implements Render {
    public List<Node> mdNodes = new ArrayList<>();
    public List<String> data = new ArrayList<>();
    public MDReader reader;
    private static final int COUNT_HEADER_MAX = 6;
    //Pattern found using StackOverflow: https://stackoverflow.com/a/70326197
    private static final String REGEX_LINK = "\\[([^\\]]+)\\]\\(([^)]+)\\)";
    private static final String REGEX_HEADER = "#+";
    private static final Pattern PATTERN_LINK = Pattern.compile(REGEX_LINK);
    private static final Pattern PATTERN_HEADER = Pattern.compile(REGEX_HEADER);
    public MDRender(String path) {
        System.out.println("About to read file");
        reader = new MDReader(path);
        data = reader.getLines();
    }

    @Override
    public List<Node> render() {


        //check if header, paragraph or text;
        for(String line : data) {
            line.trim();
            Node current = checkLine(line);
            int contentStart = 0;
            if(current == null) {
                //create paragraph
            } else {
                //if null or link, add node
                if(current.getType() == NodeType.NULL || current.getType() == NodeType.LINK) {
                    mdNodes.add(current);
                    continue;
                } else if(current.getType() == NodeType.HEADER) {
                    contentStart = current.getLevel() + 1;
                }
            }

            List<Node> children = findChildren(line.substring(contentStart, line.length()));
            for(Node child : children) {
                current.addChild(child);
            }
            mdNodes.add(current);

        }

        return mdNodes;


    }

    /**
     *
     * @return new list of nodes with all consecutive paragraph nodes linked
     */
    public List<Node> linkParagraphNodes() {
        int currentIndex = 0;
        Node current = null;
        List<Node> newNodes = new ArrayList<>();
        while(currentIndex < mdNodes.size()) {
            if(mdNodes.get(currentIndex).getType() != NodeType.PARAGRAPH) {
                if (current != null) {
                    newNodes.add(current);
                    current = null;
                }
                newNodes.add(mdNodes.get(currentIndex));
            } else {
                if(current != null) {
                    List<Node> children = current.getChildren();
                    children.addAll(mdNodes.get(currentIndex).getChildren());
                    current.setChildren(children);

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
     * Method that looks for a pattern match for a link element and generates a
     * list of parent nodes
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
     *
     * @param line
     * @return
     */
    public Node checkLink(String line) {
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

    private NullNode checkBlank(String line) {
        if(line.trim().length() == 0) {
            return new NullNode(NodeType.NULL);
        }
        return null;
    }

    /**
     *
     * @return Node of type Paragraph
     */
    private Node generateParagraphNode() {
        return new ParagraphNode(NodeType.PARAGRAPH);
    }

    /**
     *
     * @param link
     * @param name
     * @return Node of type Link
     */
    private LinkNode generateLinkNode(String link, String name) {
        LinkNode node = new LinkNode(NodeType.LINK, link);
        TextNode linkText = new TextNode(NodeType.TEXT, name);
        node.addChild(linkText);
        return node;

    }
}
