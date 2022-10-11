package org.com.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


/**
 * Class that parses the .md file and reads
 * the data into a list of lines
 */
public class MDReader implements Reader {

    private List<String> lines;
    private BufferedReader reader = null;
    private String file;

    public MDReader(String path) {

        file = path;
        lines = read(file);
    }

    @Override
    public List<String> read(String path) {
        List<String> readList = new ArrayList<String>();
        if(path.endsWith(".md")) {
            try {
                reader = new BufferedReader(new FileReader(path));
                String str;
                while ((str = reader.readLine()) != null) {
                    System.out.println(str);
                    readList.add(str);
                }
                reader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("The file is not a markdown file.");
        }

        return readList;
    }

    @Override
    public List<String> getLines() {
        return this.lines;
    }

}
