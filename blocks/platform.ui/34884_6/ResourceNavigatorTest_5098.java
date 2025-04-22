package org.eclipse.ui.tests.navigator;

import junit.framework.Test;
import junit.framework.TestSuite;

public class NavigatorTestSuite extends TestSuite {

    public static Test suite() {
        return new NavigatorTestSuite();
    }

    public NavigatorTestSuite() {
        addTest(new TestSuite(ResourceNavigatorTest.class));
        addTest(new TestSuite(NavigatorTest.class));
    }

}

