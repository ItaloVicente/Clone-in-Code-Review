package org.eclipse.ui.tests.api;

import org.eclipse.ui.tests.TestPlugin;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class StartupTest extends UITestCase {

    public StartupTest(String arg) {
        super(arg);
    }

    public void testStartup() {
        assertTrue("Startup - explicit", StartupClass.getEarlyStartupCalled());
        assertTrue("Startup - implicit", TestPlugin.getEarlyStartupCalled());
        assertTrue("Startup - completed before tests", StartupClass.getEarlyStartupCompleted());
    }

    @Override
	protected void doTearDown() throws Exception {
        super.doTearDown();
        StartupClass.reset();
        TestPlugin.clearEarlyStartup();
    }
}
