package org.eclipse.ui.tests.internal;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class Bug540297WorkbenchPageFindViewTest extends UITestCase {

	public static class MyPerspective implements IPerspectiveFactory {
		public static String ID1 = "org.eclipse.ui.tests.internal.Bug540297WorkbenchPageFindViewTest.MyPerspective1";
		public static String ID2 = "org.eclipse.ui.tests.internal.Bug540297WorkbenchPageFindViewTest.MyPerspective2";

		@Override
		public void createInitialLayout(IPageLayout layout) {
		}
	}

	public static class MyViewPart extends ViewPart {
		public static String ID = "org.eclipse.ui.tests.internal.Bug540297WorkbenchPageFindViewTest.MyViewPart";

		@Override
		public void createPartControl(Composite parent) {
			Label label = new Label(parent, SWT.NONE);
			label.setText(getSite().getId());
		}

		@Override
		public void setFocus() {
		}
	}

	private IWorkbenchWindow firstWindow;
	private IWorkbenchPage firstWindowActivePage;
	private IWorkbenchWindow secondWindow;
	private IWorkbenchPage secondWindowActivePage;
	private IPerspectiveDescriptor originalPerspective;
	private IPerspectiveDescriptor activePerspective;
	private IPerspectiveDescriptor inactivePerspective;

	public Bug540297WorkbenchPageFindViewTest(String testName) {
		super(testName);
	}

	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();

		firstWindow = fWorkbench.getActiveWorkbenchWindow();
		secondWindow = openTestWindow();

		firstWindowActivePage = firstWindow.getActivePage();
		secondWindowActivePage = secondWindow.getActivePage();

		originalPerspective = firstWindowActivePage.getPerspective();

		activePerspective = getPerspetiveDescriptor(MyPerspective.ID1);
		inactivePerspective = getPerspetiveDescriptor(MyPerspective.ID2);

		firstWindowActivePage.setPerspective(activePerspective);
		prepareWorkbenchPageForTest(firstWindowActivePage);
		prepareWorkbenchPageForTest(secondWindowActivePage);

		processEvents();
	}

	private void prepareWorkbenchPageForTest(IWorkbenchPage page) {
		page.setPerspective(inactivePerspective);
		page.resetPerspective();
		page.closeAllEditors(false);
		page.setPerspective(activePerspective);
		page.resetPerspective();
		page.closeAllEditors(false);
		IViewReference[] views = page.getViewReferences();
		for (IViewReference view : views) {
			page.hideView(view);
		}
	}

	@Override
	protected void doTearDown() throws Exception {
		secondWindow.close();
		firstWindowActivePage.setPerspective(originalPerspective);
		firstWindowActivePage.resetPerspective();
		firstWindowActivePage.closePerspective(inactivePerspective, false, false);
		firstWindowActivePage.closePerspective(activePerspective, false, false);
		processEvents();
		super.doTearDown();
	}

	public void testFindViewInFirstWindowAndActivePerspective() throws Exception {
		showView(firstWindowActivePage);
		assertCanFindView(firstWindowActivePage);
		assertCannotFindView(secondWindowActivePage);
	}

	public void testFindViewInFirstWindowAndInactivePerspective() throws Exception {
		showViewInInactivePerspective(firstWindowActivePage);
		showAndHideView(firstWindowActivePage);
		assertCannotFindView(firstWindowActivePage);
		assertCannotFindView(secondWindowActivePage);
	}

	public void testFindViewInSecondWindowAndActivePerspective() throws Exception {
		showView(secondWindowActivePage);
		assertCanFindView(secondWindowActivePage);
		assertCannotFindView(firstWindowActivePage);
	}

	public void testFindViewInSecondWindowAndInactivePerspective() throws Exception {
		showViewInInactivePerspective(secondWindowActivePage);
		showAndHideView(secondWindowActivePage);
		assertCannotFindView(secondWindowActivePage);
		assertCannotFindView(firstWindowActivePage);
	}

	private void showViewInInactivePerspective(IWorkbenchPage pageForTest) throws Exception {
		setPerspective(pageForTest, inactivePerspective);
		showView(pageForTest);
		setPerspective(pageForTest, activePerspective);
	}

	private static void setPerspective(IWorkbenchPage page, IPerspectiveDescriptor perspective) {
		page.setPerspective(perspective);
		page.resetPerspective();
		processEvents();
	}

	private static void showAndHideView(IWorkbenchPage page) throws Exception {
		showView(page);
		hideView(page);
	}

	private static void showView(IWorkbenchPage page) throws Exception {
		page.showView(MyViewPart.ID);
		processEvents();
	}

	private static void hideView(IWorkbenchPage page) throws Exception {
		IViewPart view = page.findView(MyViewPart.ID);
		if (view != null) {
			page.hideView(view);
		}
		processEvents();
	}

	private IPerspectiveDescriptor getPerspetiveDescriptor(String perspectiveId) {
		return fWorkbench.getPerspectiveRegistry().findPerspectiveWithId(perspectiveId);
	}

	private static void assertCanFindView(IWorkbenchPage page) throws Exception {
		assertFindViewResult(page, true);
	}

	private static void assertCannotFindView(IWorkbenchPage page) throws Exception {
		assertFindViewResult(page, false);
	}

	private static void assertFindViewResult(IWorkbenchPage page, boolean expectedFound) throws Exception {
		IViewPart viewPart = page.findView(MyViewPart.ID);
		boolean actualFound = viewPart != null;
		assertEquals("unexpected result from IWorkbenchPage.findView(String): " + viewPart, expectedFound, actualFound);
	}

}
