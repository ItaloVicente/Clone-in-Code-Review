package org.eclipse.jface.tests.action;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests extends TestSuite {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite() {
        return new AllTests();
    }

    public AllTests() {
        addTestSuite(ContributionItemTest.class);
        addTestSuite(ToolBarManagerTest.class);
        addTestSuite(CoolBarManagerTest.class);
        addTestSuite(MenuManagerTest.class);
    }
}
