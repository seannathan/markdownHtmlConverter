package org.com;


/**
 * Hello world!
 *
 */
public class App  {
    public static void main( String[] args ) {

            System.out.println( "Hello World!" );
            MDRender render = new MDRender("src/md_test_1.md");
            render.render();
            HTMLGenerator generator = new HTMLGenerator(render.mdNodes);
            generator.testHTML();
    }
}
