package org.com;

import org.com.node.Node;
import org.com.util.MDReader;
import org.com.util.Render;

import java.util.*;

public class MDRender implements Render {
    List<Node> nodes = new ArrayList<>();
    List<String> data = new ArrayList<>();
    MDReader reader;
    public MDRender(String path) {
        reader = new MDReader(path);
        data = reader.getLines();
    }

    @Override
    public List<Node> render() {


        //check if header, paragraph or text;

        return null;


    }
}
