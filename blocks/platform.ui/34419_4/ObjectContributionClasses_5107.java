package org.eclipse.ui.tests.menus;


import junit.framework.Test;
import junit.framework.TestSuite;


public class MenusTestSuite extends TestSuite {

    public static Test suite() {
        return new MenusTestSuite();
    }

    public MenusTestSuite() {
        addTest(new TestSuite(ObjectContributionTest.class));
        addTest(new TestSuite(MenuVisibilityTest.class));
        addTest(new TestSuite(MenuBaseTests.class));
        addTest(new TestSuite(MenuPopulationTest.class));
        addTest(new TestSuite(DynamicMenuTest.class));
        addTest(new TestSuite(Bug231304Test.class));
        addTest(new TestSuite(ShowViewMenuTest.class));
        addTest(new TestSuite(Bug264804Test.class));
        addTest(new TestSuite(MenuHelperTest.class));
        addTest(new TestSuite(Bug410426Test.class));
    }
}
