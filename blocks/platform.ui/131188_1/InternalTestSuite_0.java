package org.eclipse.ui.tests.internal;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
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
	private IPerspectiveDescriptor firstPerspective;
	private IPerspectiveDescriptor secondPerspective;

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

		firstPerspective = getPerspetiveDescriptor(MyPerspective.ID1);
		secondPerspective = getPerspetiveDescriptor(MyPerspective.ID2);
		prepareWorkbenchPageForTest(firstWindowActivePage);
		prepareWorkbenchPageForTest(secondWindowActivePage);

		processEvents();
	}

	private void prepareWorkbenchPageForTest(IWorkbenchPage secondWindowActivePage2) {
		secondWindowActivePage2.setPerspective(secondPerspective);
		secondWindowActivePage2.resetPerspective();
		secondWindowActivePage2.closeAllEditors(false);
		secondWindowActivePage2.setPerspective(firstPerspective);
		secondWindowActivePage2.resetPerspective();
		secondWindowActivePage2.closeAllEditors(false);
	}

	@Override
	protected void doTearDown() throws Exception {
		secondWindow.close();
		firstWindowActivePage.setPerspective(originalPerspective);
		firstWindowActivePage.resetPerspective();
		processEvents();
		super.doTearDown();
	}

	public void testFindViewInFirstWindowAndFirstPerspective() throws Exception {
		showView(firstWindowActivePage);
		assertCanFindView(firstWindowActivePage);
		assertCannotFindView(secondWindowActivePage);
	}

	public void testFindViewInFirstWindowAndSecondPerspective() throws Exception {
		setPerspective(firstWindowActivePage, secondPerspective);
		showView(firstWindowActivePage);
		setPerspective(firstWindowActivePage, firstPerspective);
		assertCannotFindView(firstWindowActivePage);
		assertCannotFindView(secondWindowActivePage);
	}

	public void testFindViewInSecondWindowAndFirstPerspective() throws Exception {
		showView(secondWindowActivePage);
		assertCanFindView(secondWindowActivePage);
		assertCannotFindView(firstWindowActivePage);
	}

	public void testFindViewInSecondWindowAndSecondPerspective() throws Exception {
		setPerspective(secondWindowActivePage, secondPerspective);
		showView(secondWindowActivePage);
		setPerspective(secondWindowActivePage, firstPerspective);
		assertCannotFindView(secondWindowActivePage);
		assertCannotFindView(firstWindowActivePage);
	}

	private static void setPerspective(IWorkbenchPage page, IPerspectiveDescriptor perspective) {
		page.setPerspective(perspective);
		page.resetPerspective();
		processEvents();
	}

	private static void showView(IWorkbenchPage page) throws PartInitException {
		page.showView(MyViewPart.ID);
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
