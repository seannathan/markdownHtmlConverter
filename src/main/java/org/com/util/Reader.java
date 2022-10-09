package org.com.util;

import java.io.IOException;
import java.util.*;

public interface Reader {

    List<String> read(String path);
    List<String> getLines();

}
