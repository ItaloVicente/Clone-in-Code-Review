package org.eclipse.ui.tests.rcp;

import junit.framework.Test;
import junit.framework.TestSuite;

public class RcpTestSuite extends TestSuite {

    public static Test suite() {
        return new RcpTestSuite();
    }

    public RcpTestSuite() {
        addTest(PlatformUITest.suite());
        addTest(new TestSuite(WorkbenchAdvisorTest.class));
        addTest(new TestSuite(WorkbenchConfigurerTest.class));
        addTest(new TestSuite(WorkbenchWindowConfigurerTest.class));
        addTest(new TestSuite(ActionBarConfigurerTest.class));
        addTest(new TestSuite(IWorkbenchPageTest.class));
        addTest(new TestSuite(WorkbenchSaveRestoreStateTest.class));
        addTest(new TestSuite(WorkbenchListenerTest.class));
    }
}
