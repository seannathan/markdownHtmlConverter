package org.com.node;

import java.util.*;

public abstract class Node {

    private NodeType type;
    private List<Node> children = new ArrayList<>();
    private String text;
    private int level;

//    public Node(NodeType t) {
//        this.type = t;
//    }
//
//    public Node(NodeType t, String line) {
//        this.type = t;
//        this.text = line;
//    }

//    public Node() {
//
//    }

    public List<Node> addChild(Node child) {
        children.add(child);
        return children;
    }

    public NodeType getType() {
        return this.type;
    }

    public void setChildren(List<Node> children) { this.children = children; }

    public List<Node> getChildren() {
        return this.children;
    }

    public int getLevel() {return this.level;}

    public void setText(String text) { this.text = text; }

    public String getText() { return this.text; }


}
