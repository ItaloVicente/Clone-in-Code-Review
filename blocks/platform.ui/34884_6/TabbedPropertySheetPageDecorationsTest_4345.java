package org.eclipse.ui.tests.views.properties.tabbed;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class AllTests
    extends TestCase {

    public static void main(String[] args) {
        TestRunner.run(suite());
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(TabbedPropertySheetPageTest.class);
        suite.addTestSuite(TabbedPropertySheetPageDynamicTest.class);
        suite.addTestSuite(TabbedPropertySheetPageTextTest.class);
        suite.addTestSuite(TabbedPropertySheetPageOverrideTest.class);
        suite.addTestSuite(TabbedPropertySheetPageDecorationsTest.class);
        return suite;
    }

}
