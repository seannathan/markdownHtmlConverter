package org.com;


/**
 * Hello world!
 *
 */
public class App  {
    public static void main( String[] args ) {

            System.out.println( "Hello World!" );
            MDRender render = new MDRender("src/mailchimpSampleTwo.md");
            render.render();
            HTMLGenerator generator = new HTMLGenerator(render.getMdNodes());
            generator.testHTML("appMain.html");
    }
}
