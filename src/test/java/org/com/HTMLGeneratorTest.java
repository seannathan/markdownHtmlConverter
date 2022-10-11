package org.com;

import org.junit.Test;

public class HTMLGeneratorTest {

    private String testFileOne = "src/linkTest.md";
    private String testFileTwo = "src/paragraphTest.md";
    private String testFileThree = "src/mailchimpSampleOne.md";
    private String testFileFour = "src/mailchimpSampleTwo.md";

    private String outputFileOne = "src/htmlTestOne.md";
    private String outputFileTwo = "src/htmlTestTwo.md";
    private String outputFileThree = "src/htmlTestThree.md";
    private String outputFileFour = "src/htmlTestFour.md";

    private MDRender renderOne = new MDRender(testFileOne);
    private MDRender renderTwo = new MDRender(testFileTwo);
    private MDRender renderThree = new MDRender(testFileThree);
    private MDRender renderFour = new MDRender(testFileFour);

    @Test
    public void testFileOne() {
        renderOne.render();
        HTMLGenerator generatorOne = new HTMLGenerator(renderOne.getMdNodes());
        generatorOne.testHTML(outputFileOne);
    }

    @Test
    public void testFileTwo() {
        renderTwo.render();
        HTMLGenerator generatorTwo = new HTMLGenerator(renderTwo.getMdNodes());
        generatorTwo.testHTML(outputFileTwo);
    }

    @Test
    public void testFileThree() {
        renderThree.render();
        HTMLGenerator generatorThree = new HTMLGenerator(renderThree.getMdNodes());
        generatorThree.testHTML(outputFileThree);
    }

    @Test
    public void testFileFour() {
        renderFour.render();
        HTMLGenerator generatorFour = new HTMLGenerator(renderFour.getMdNodes());
        generatorFour.testHTML(outputFileFour);
    }


}
