package org.eclipse.ui.tests.quickaccess;

import junit.framework.Test;
import junit.framework.TestSuite;

public class QuickAccessTestSuite extends TestSuite {

    public static Test suite() {
        return new QuickAccessTestSuite();
    }

    public QuickAccessTestSuite() {
        addTest(new TestSuite(CamelUtilTest.class));
        addTest(new TestSuite(QuickAccessDialogTest.class));
    }
}
