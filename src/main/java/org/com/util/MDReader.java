package org.com.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


/**
 * Class that par
 */
public class MDReader implements Reader {

    private List<String> lines = new ArrayList<>();
    private BufferedReader reader = null;
    private String file;

    public MDReader(String path) {
        file = path;
    }

    @Override
    public List<String> read(String path) throws IOException {
        List<String> readList = new ArrayList<String>();
        if(path.endsWith(".md")) {
            try {
                reader = new BufferedReader(new FileReader(path));
                String str;
                while ((str = reader.readLine()) != null) {
                    readList.add(str);
                }
                reader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new IOException("File is not a markdown file.");
        }

        return readList;
    }

    @Override
    public List<String> getLines() {
        return this.lines;
    }

}
