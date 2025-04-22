
package org.eclipse.ui.tests.markers;

import junit.framework.TestSuite;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class MarkersViewColumnSizeTest extends UITestCase {
	
	public static TestSuite suite() {
		TestSuite ts = new TestSuite("org.eclipse.ui.tests.markers.MarkersViewColumnSizeTest");
		ts.addTest(new MarkersViewColumnSizeTest("testColumnCreate"));
		ts.addTest(new MarkersViewColumnSizeTest("testColumnRestore"));
		return ts;
	}

	public MarkersViewColumnSizeTest(String name) {
		super(name);
	}

	public void testColumnCreate() {
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		if (window == null)
			assertTrue("Could not get a workbench window", false);
		IWorkbenchPage page = window.getActivePage();
		if (page == null)
			assertTrue("Could not get a workbench page", false);

		MarkersTestMarkersView problemView;
		try {
			problemView = (MarkersTestMarkersView) page
					.showView("org.eclipse.ui.tests.markerTests");
		} catch (PartInitException e) {
			assertTrue(e.getLocalizedMessage(), false);
			return;
		}

		problemView.setColumnWidths(100);

	}
	
	public void testColumnRestore() {
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		if (window == null)
			assertTrue("Could not get a workbench window", false);
		IWorkbenchPage page = window.getActivePage();
		if (page == null)
			assertTrue("Could not get a workbench page", false);

		MarkersTestMarkersView problemView;
		try {
			problemView = (MarkersTestMarkersView) page
					.showView("org.eclipse.ui.tests.markerTests");
		} catch (PartInitException e) {
			assertTrue(e.getLocalizedMessage(), false);
			return;
		}

		assertTrue("Column sizes not restored", problemView
				.checkColumnSizes(100));
	}
}
