package org.eclipse.ui.tests.session;

import org.eclipse.ui.tests.harness.util.RCPTestWorkbenchAdvisor;

import junit.framework.TestCase;
import junit.framework.TestSuite;


public class RestoreSessionTest extends TestCase {
	public static TestSuite suite() {
		TestSuite ts = new TestSuite("org.eclipse.ui.tests.session.RestoreSessionTest");
		ts.addTest(new RestoreSessionTest("testDisplayAccess"));
		return ts;
	}
	
	public RestoreSessionTest(String name) {
		super(name);
	}

	public void testDisplayAccess() {
		assertNotNull(RCPTestWorkbenchAdvisor.syncWithoutDisplayAccess);
		assertNotNull(RCPTestWorkbenchAdvisor.syncWithDisplayAccess);
		
		assertNotNull(RCPTestWorkbenchAdvisor.asyncWithDisplayAccess);
		assertNotNull(RCPTestWorkbenchAdvisor.asyncWithoutDisplayAccess);
		
		assertNotNull(RCPTestWorkbenchAdvisor.asyncDuringStartup);
		
		assertEquals(Boolean.FALSE, RCPTestWorkbenchAdvisor.asyncDuringStartup);
		
		assertEquals(Boolean.TRUE, RCPTestWorkbenchAdvisor.syncWithDisplayAccess);
		assertEquals(Boolean.TRUE, RCPTestWorkbenchAdvisor.asyncWithDisplayAccess);
		
		assertEquals(Boolean.FALSE, RCPTestWorkbenchAdvisor.syncWithoutDisplayAccess);
		assertEquals(Boolean.FALSE, RCPTestWorkbenchAdvisor.asyncWithoutDisplayAccess);
		
		assertFalse(RCPTestWorkbenchAdvisor.displayAccessInUIThreadAllowed);
	}
}
