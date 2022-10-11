package org.com;


/**
 * Hello world!
 *
 */
public class App  {
    public static void main( String[] args ) {

            MDRender render = new MDRender("src/linkTest.md");
            render.render();
            HTMLGenerator generator = new HTMLGenerator(render.getMdNodes());
            generator.testHTML("appMain.html");
    }
}
