
package org.eclipse.ui.tests.session;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.PropertySheet;

public class NonRestorablePropertySheetTest extends TestCase {

	public static TestSuite suite() {
		TestSuite ts = new TestSuite("org.eclipse.ui.tests.session.NonRestorablePropertySheetTest");
		ts.addTest(new NonRestorablePropertySheetTest("test01ActivateView"));
		ts.addTest(new NonRestorablePropertySheetTest("test02SecondOpening"));
		return ts;
	}

	public NonRestorablePropertySheetTest(String testName) {
		super(testName);
	}

	public void test01ActivateView() throws PartInitException {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final IWorkbenchPage page = workbench.getActiveWorkbenchWindow()
				.getActivePage();

		IViewPart part = page.showView(IPageLayout.ID_PROP_SHEET);
		assertNotNull(part);
		assertTrue(part instanceof PropertySheet);

		for (int j = 0; j < 3; j++) {
			try {
				page.showView(IPageLayout.ID_PROP_SHEET, "#" + j,
						IWorkbenchPage.VIEW_ACTIVATE);
			} catch (PartInitException e) {
				fail(e.getMessage());
			}
		}
		assertTrue(countPropertySheetViews(page) == 4);
	}

	public void test02SecondOpening() throws PartInitException {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final IWorkbenchPage page = workbench.getActiveWorkbenchWindow()
				.getActivePage();

		assertTrue(countPropertySheetViews(page) == 1);
	}

	private int countPropertySheetViews(final IWorkbenchPage page) {
		int count = 0;
		IViewReference[] views = page.getViewReferences();
		for (int i = 0; i < views.length; i++) {
			IViewReference ref = views[i];
			if (ref.getId().equals(IPageLayout.ID_PROP_SHEET)) {
				count++;
			}
		}
		return count;
	}

}
