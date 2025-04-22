
package org.eclipse.ui.tests.session;

import junit.framework.TestSuite;

import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.tests.harness.util.UITestCase;


public class WindowlessSessionTest extends UITestCase {
	public static TestSuite suite() {
		TestSuite ts = new TestSuite("org.eclipse.ui.tests.session.WindowlessSessionTest");
		ts.addTest(new WindowlessSessionTest("testWindowlessWorkbench"));
		return ts;
	}

	public WindowlessSessionTest(String name) {
		super(name);
	}

	public void testWindowlessWorkbench() throws Exception {

		assertTrue(fWorkbench.getWorkbenchWindowCount() == 0);

		IWorkbenchWindow window = fWorkbench.openWorkbenchWindow(null);

		assertTrue(fWorkbench.getWorkbenchWindowCount() == 1);

		window.close();

		assertTrue(fWorkbench.getWorkbenchWindowCount() == 0);
	}
}
