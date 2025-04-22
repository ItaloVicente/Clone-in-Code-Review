package org.eclipse.ui.tests.session;

import java.util.List;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.MUILabel;
import org.eclipse.e4.ui.model.application.ui.advanced.MPlaceholder;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.ViewSite;

public class Bug108033Test extends TestCase {

	public static TestSuite suite() {
		TestSuite ts = new TestSuite("org.eclipse.ui.tests.session.Bug108033Test");
		ts.addTest(new Bug108033Test("testShowMultipleViews"));
		ts.addTest(new Bug108033Test("testCheckMultipleViews"));
		ts.addTest(new Bug108033Test("testMovedMultipleViews"));
		return ts;
	}

	public static final String PROBLEM_VIEW_ID = "org.eclipse.ui.views.ProblemView";

	public static final String TASK_VIEW_ID = "org.eclipse.ui.views.TaskList";

	public static final String PROGRESS_VIEW_ID = "org.eclipse.ui.views.ProgressView";

	private static String RESOURCE_ID = "org.eclipse.ui.resourcePerspective";

	private IWorkbenchWindow fWin;

	private IWorkbenchPage fActivePage;

	private IWorkbench fWorkbench;

	public Bug108033Test(String testName) {
		super(testName);
	}

	@Override
	protected void setUp() throws Exception {
		fWorkbench = PlatformUI.getWorkbench();

		fWin = fWorkbench.getActiveWorkbenchWindow();

		fActivePage = fWin.getActivePage();
	}

	public void testShowMultipleViews() throws Throwable {
		IPerspectiveDescriptor desc = fActivePage.getWorkbenchWindow()
				.getWorkbench().getPerspectiveRegistry().findPerspectiveWithId(
						RESOURCE_ID);
		fActivePage.setPerspective(desc);
		fActivePage.resetPerspective();
		assertNotNull(fActivePage.showView(TASK_VIEW_ID));
		assertNotNull(fActivePage.showView(PROGRESS_VIEW_ID));
		assertNotNull(fActivePage.showView(PROBLEM_VIEW_ID));
	}

	public void testCheckMultipleViews() throws Throwable {
		IViewPart problemView = instantiateViews();

		ViewSite site = (ViewSite) problemView.getSite();
		MElementContainer<MUIElement> stack = getParent(site.getModel());

		verifyOrder(stack, new String[] { "Tasks", "Progress", "Problems" });
		moveTab(stack, problemView, 0);

		verifyOrder(stack, new String[] { "Problems", "Tasks", "Progress" });
	}

	private MElementContainer<MUIElement> getParent(MUIElement element) {
		MElementContainer<MUIElement> parent = element.getParent();
		if (parent != null) {
			return parent;
		}
		MPlaceholder placeholder = element.getCurSharedRef();
		if (placeholder != null) {
			return placeholder.getParent();
		}
		return null;
	}

	private void moveTab(MElementContainer<MUIElement> stack, IViewPart viewPart, int indexTo) {
		ViewSite site = (ViewSite) viewPart.getSite();
		MPart part = site.getModel();
		List<MUIElement> children = stack.getChildren();
		int indexFrom = children.indexOf(part);
		assertTrue(indexFrom >= 0);
		children.remove(part);
		children.add(indexTo, part);
		stack.setSelectedElement(part);
	}

	public void testMovedMultipleViews() throws Throwable {
		IViewPart problemView = instantiateViews();

		ViewSite site = (ViewSite) problemView.getSite();
		MElementContainer<MUIElement> stack = getParent(site.getModel());

		verifyOrder(stack, new String[] { "Problems", "Tasks", "Progress" });
	}

	private IViewPart instantiateViews() throws PartInitException {
		IViewPart problemView = fActivePage.showView(PROBLEM_VIEW_ID);
		assertNotNull(problemView);

		assertNotNull(fActivePage.showView(PROGRESS_VIEW_ID));
		assertNotNull(fActivePage.showView(TASK_VIEW_ID));
		return problemView;
	}

	private void verifyOrder(MElementContainer<MUIElement> stack, String[] order) {
		List<MUIElement> children = stack.getChildren();
		assertEquals("Different number of tabs", order.length, children.size());
		for (int i = 0; i < children.size(); ++i) {
			MUIElement child = children.get(i);
			if (child instanceof MPlaceholder) {
				child = ((MPlaceholder) child).getRef();
			}
			assertEquals("Failed on tab " + i, order[i], ((MUILabel)child).getLabel());
		}
	}

}
