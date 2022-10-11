package org.com.node;

import java.util.*;

/**
 * Generalized node class
 */
public abstract class Node {

    private NodeType type;
    private List<Node> children = new ArrayList<>();
    private String text;
    private int level;

    public Node(NodeType t) {
        this.type = t;
    }

    public Node(NodeType t, String line) {
        this.type = t;
        this.text = line;
    }

    public Node(NodeType t, int level) {
        this.type = t;
        this.level = level;
    }

    public List<Node> addChild(Node child) {
        children.add(child);
        return children;
    }

    public NodeType getType() {
        return this.type;
    }

    public List<Node> getChildren() {
        return this.children;
    }

    public int getLevel() {return this.level;}

    public String getText() { return this.text; }


}
