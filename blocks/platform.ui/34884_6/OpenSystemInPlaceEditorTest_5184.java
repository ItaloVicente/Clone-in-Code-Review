package org.eclipse.ui.tests.stress;

import junit.framework.Test;
import junit.framework.TestSuite;

public class OpenCloseTestSuite extends TestSuite {

    public static Test suite() {
        return new OpenCloseTestSuite();
    }

    public OpenCloseTestSuite() {
        addTest(new TestSuite(OpenCloseTest.class));

    }
}
