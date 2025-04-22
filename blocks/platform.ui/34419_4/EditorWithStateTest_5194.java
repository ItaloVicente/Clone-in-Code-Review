package org.eclipse.ui.tests.session;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

public class Bug98800Test extends TestCase {
	private static final String PROBLEM_VIEW_ID = "org.eclipse.ui.views.ProblemView";

	private static final String VIEW_WITH_STATE_ID = "org.eclipse.ui.tests.session.ViewWithState";

	public static TestSuite suite() {
		TestSuite ts = new TestSuite("org.eclipse.ui.tests.session.Bug98800Test");
		ts.addTest(new Bug98800Test("testActivateView"));
		ts.addTest(new Bug98800Test("testSecondOpening"));
		ts.addTest(new Bug98800Test("testSavedMemento"));
		return ts;
	}

	private IWorkbenchPage fPage;

	public Bug98800Test(String testName) {
		super(testName);
		fPage = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage();
	}

	public void testActivateView() throws Throwable {
		IViewPart v = fPage.showView(VIEW_WITH_STATE_ID);

		fPage.showView(PROBLEM_VIEW_ID);

		ViewWithState view = (ViewWithState) v;
		view.fState = 10;
	}

	public void testSecondOpening() throws Throwable {
		IViewReference[] views = fPage.getViewReferences();
		for (IViewReference ref : views) {
			if (ref.getId().equals(VIEW_WITH_STATE_ID)) {
				assertNull("The view should not be instantiated", ref
						.getPart(false));
			}
		}
	}

	public void testSavedMemento() throws Throwable {
		IViewPart v = fPage.showView(VIEW_WITH_STATE_ID);
		ViewWithState view = (ViewWithState) v;
		assertEquals(
				"the view state should have made it through a session without instantiation",
				10, view.fState);

		fPage.hideView(v);
		v = fPage.showView(VIEW_WITH_STATE_ID);
		view = (ViewWithState) v;
		assertEquals("The view state should be reset", 0, view.fState);
	}
}
