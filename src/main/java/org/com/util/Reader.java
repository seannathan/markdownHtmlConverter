package org.com.util;

import java.io.IOException;
import java.util.*;

public interface Reader {

    List<String> read(String path) throws IOException;
    List<String> getLines();

}
