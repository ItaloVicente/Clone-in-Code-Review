package org.eclipse.ui.tests.session;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

public class NonRestorableViewTest extends TestCase {

	private static final String NON_RESTORABLE_VIEW_ID = "org.eclipse.ui.tests.session.NonRestorableView";

	public static TestSuite suite() {
		TestSuite ts = new TestSuite("org.eclipse.ui.tests.session.NonRestorableViewTest");
		ts.addTest(new NonRestorableViewTest("test01ActivateView"));
		ts.addTest(new NonRestorableViewTest("test02SecondOpening"));
		return ts;
	}

	public NonRestorableViewTest(String testName) {
		super(testName);
	}

	public void test01ActivateView() throws Throwable {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final IWorkbenchPage page = workbench.getActiveWorkbenchWindow()
				.getActivePage();

		IViewPart part = page.showView(NON_RESTORABLE_VIEW_ID);
		assertNotNull(part);
		assertTrue(part instanceof NonRestorableView);
	}

	public void test02SecondOpening() throws Throwable {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final IWorkbenchPage page = workbench.getActiveWorkbenchWindow()
				.getActivePage();

		IViewReference[] views = page.getViewReferences();
		for (int i = 0; i < views.length; i++) {
			IViewReference ref = views[i];
			if (ref.getId().equals(NON_RESTORABLE_VIEW_ID)) {
				fail("Should not find this view");
			}
		}
	}
}
