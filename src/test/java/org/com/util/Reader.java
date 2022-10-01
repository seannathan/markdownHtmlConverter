package org.com.util;

import java.io.*;
import java.util.*;

public class Reader {
    public List<String> lines = new ArrayList<>();
    BufferedReader reader = null;
    public Reader(String file) {
        lines = read(file);
    }

    private List<String> read(String path) {
        List<String> readList = new ArrayList<String>();
        try {
            reader = new BufferedReader(new FileReader("myfile.txt"));
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

        return readList;
    }
}
