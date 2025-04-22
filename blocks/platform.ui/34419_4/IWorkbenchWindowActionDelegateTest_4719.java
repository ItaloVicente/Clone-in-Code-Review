package org.eclipse.ui.tests.api;

import junit.framework.Test;
import junit.framework.TestSuite;

public class IWorkbenchTestSuite extends TestSuite {

    public static Test suite() {
        return new IWorkbenchTestSuite();
    }

    public IWorkbenchTestSuite() {
        addTest(new TestSuite(IWorkbenchTest.class));
        addTest(new TestSuite(IWorkbenchWindowTest.class));
    }
}
