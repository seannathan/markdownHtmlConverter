package org.com.node;

import java.util.*;

public abstract class Node {

    public NodeType type;
    public Map<String, String> params = new HashMap<>();
    public List<Node> children = new ArrayList<>();

    public Node(NodeType t) {
        this.type = t;
    }

    public Node() {

    }

    public NodeType getType() {
        return this.type;
    }

    public Map<String, String> getParams() {
        return this.params;
    }

    public List<Node> getChildren() {
        return this.children;
    }






}
