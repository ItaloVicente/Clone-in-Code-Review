package org.eclipse.ui.tests.session;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart3;
import org.eclipse.ui.PlatformUI;

public class ArbitraryPropertiesViewTest extends TestCase {

	private static final String USER_PROP = "org.eclipse.ui.tests.users";

	private static final String PROBLEM_VIEW_ID = "org.eclipse.ui.views.ProblemView";

	private static final String VIEW_WITH_STATE_ID = "org.eclipse.ui.tests.session.ViewWithState";

	public static TestSuite suite() {
		TestSuite ts = new TestSuite("org.eclipse.ui.tests.session.ArbitraryPropertiesViewTest");
		ts.addTest(new ArbitraryPropertiesViewTest("test01ActivateView"));
		ts.addTest(new ArbitraryPropertiesViewTest("test02SecondOpening"));
		ts.addTest(new ArbitraryPropertiesViewTest("test03PartInstantiation"));
		return ts;
	}

	public ArbitraryPropertiesViewTest(String testName) {
		super(testName);
	}

	public void test01ActivateView() throws Throwable {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final IWorkbenchPage page = workbench.getActiveWorkbenchWindow()
				.getActivePage();

		IViewPart v = page.showView(VIEW_WITH_STATE_ID);

		page.showView(PROBLEM_VIEW_ID);

		IWorkbenchPart3 wp = (IWorkbenchPart3) v;
		wp.setPartProperty(USER_PROP, "pwebster");
	}

	public void test02SecondOpening() throws Throwable {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final IWorkbenchPage page = workbench.getActiveWorkbenchWindow()
				.getActivePage();

		IViewReference[] views = page.getViewReferences();
		for (IViewReference ref : views) {
			if (ref.getId().equals(VIEW_WITH_STATE_ID)) {
				assertNull("The view should not be instantiated", ref
						.getPart(false));
				assertEquals("pwebster", ref.getPartProperty(USER_PROP));
			}
		}
	}

	static class PropListener implements IPropertyChangeListener {
		public int count = 0;

		@Override
		public void propertyChange(PropertyChangeEvent event) {
			count++;
		}
	};

	public void test03PartInstantiation() throws Throwable {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final IWorkbenchPage page = workbench.getActiveWorkbenchWindow()
				.getActivePage();

		IViewReference ref = page.findViewReference(VIEW_WITH_STATE_ID);
		assertEquals("pwebster", ref.getPartProperty(USER_PROP));
		PropListener listener = new PropListener();
		ref.addPartPropertyListener(listener);

		IViewPart v = null;
		try {
			v = page.showView(VIEW_WITH_STATE_ID);
			IWorkbenchPart3 wp = (IWorkbenchPart3) v;
			assertEquals("pwebster", wp.getPartProperty(USER_PROP));
			assertEquals(0, listener.count);
		} finally {
			ref.removePartPropertyListener(listener);
		}
		page.hideView(v);
		v = page.showView(VIEW_WITH_STATE_ID);
		IWorkbenchPart3 wp = (IWorkbenchPart3) v;
		assertNull(wp.getPartProperty(USER_PROP));
	}
}
