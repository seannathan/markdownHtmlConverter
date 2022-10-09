package org.com;

import org.com.node.Node;


import java.util.*;

public class HTMLGenerator {
    private List<Node> nodes;
    private static Map<Integer, String> MAP_HEADER = new HashMap<Integer, String>(){{
       put(1, "h1");
       put(2, "h2");
       put(3, "h3");
       put(4, "h4");
       put(5, "h5");
       put(6, "h6");
    }};
    public HTMLGenerator(List<Node> nodes) {
        this.nodes = nodes;
    }
}
