package org.eclipse.ui.tests.rcp;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ PlatformUITest.class, WorkbenchAdvisorTest.class, WorkbenchConfigurerTest.class,
		WorkbenchWindowConfigurerTest.class, ActionBarConfigurerTest.class, IWorkbenchPageTest.class,
		WorkbenchSaveRestoreStateTest.class, WorkbenchListenerTest.class })
public class RcpTestSuite {



    public RcpTestSuite() {
    }
}
