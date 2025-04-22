
package org.eclipse.ui.tests.internal;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.tests.harness.util.UITestCase;
import org.junit.Test;

public class HideViewInNewPerspectiveTest extends UITestCase {

	public static String TEST_PERSPECTIVE_ID = "org.eclipse.ui.tests.internal.HideViewInNewPerspectiveTest.TestPerspective";
	public static String ACTIVE_VIEW_ID = "org.eclipse.ui.tests.internal.HideViewInNewPerspectiveTest.TestViewActive";
	public static String INACTIVE_VIEW_ID = "org.eclipse.ui.tests.internal.HideViewInNewPerspectiveTest.TestViewInactive";

	public static class TestPerspective implements IPerspectiveFactory {

		@Override
		public void createInitialLayout(IPageLayout layout) {
			String editorArea = layout.getEditorArea();
			IFolderLayout folder = layout.createFolder("tabs", IPageLayout.LEFT, .75f, editorArea);
			folder.addView(ACTIVE_VIEW_ID);
			folder.addView(INACTIVE_VIEW_ID);
		}
	}

	public static class TestView extends ViewPart {

		@Override
		public void createPartControl(Composite parent) {
			Label label = new Label(parent, SWT.NONE);
			label.setText(getSite().getId());
		}

		@Override
		public void setFocus() {
		}
	}

	public HideViewInNewPerspectiveTest(String testName) {
		super(testName);
	}

	@Test
	public void testInactiveViewWithViewReference() throws Exception {
		InactiveViewHideListener listener = new InactiveViewHideListener();

		IWorkbenchWindow activeWorkbenchWindow = openTestWindow();
		IWorkbench workbench = activeWorkbenchWindow.getWorkbench();
		IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();

		activePage.addPartListener(listener);
		try {
			workbench.showPerspective(TEST_PERSPECTIVE_ID, activeWorkbenchWindow);
			processUiEvents();

			IViewReference inactiveViewReference = activePage.findViewReference(INACTIVE_VIEW_ID);
			assertNotNull(inactiveViewReference);

			activePage.hideView(inactiveViewReference);
			processUiEvents();

			assertTrue("no part hide notification received after hiding view", listener.wasHidden());

			inactiveViewReference = activePage.findViewReference(INACTIVE_VIEW_ID);
			assertNull("found view reference after hiding the view", inactiveViewReference);
		} finally {
			activePage.removePartListener(listener);
		}
	}

	private void processUiEvents() {
		while (fWorkbench.getDisplay().readAndDispatch()) {
		}
	}

	static boolean isInactiveView(IWorkbenchPartReference partRef) {
		return INACTIVE_VIEW_ID.equals(partRef.getPartName());
	}

	private static class InactiveViewHideListener implements IPartListener2 {

		private boolean wasHidden = false;

		@Override
		public void partVisible(IWorkbenchPartReference partRef) {
			if (isInactiveView(partRef)) {
				wasHidden = false;
			}
		}

		@Override
		public void partOpened(IWorkbenchPartReference partRef) {
		}

		@Override
		public void partInputChanged(IWorkbenchPartReference partRef) {
		}

		@Override
		public void partHidden(IWorkbenchPartReference partRef) {
			if (isInactiveView(partRef)) {
				wasHidden = true;
			}
		}

		@Override
		public void partDeactivated(IWorkbenchPartReference partRef) {
		}

		@Override
		public void partClosed(IWorkbenchPartReference partRef) {
		}

		@Override
		public void partBroughtToTop(IWorkbenchPartReference partRef) {
		}

		@Override
		public void partActivated(IWorkbenchPartReference partRef) {
		}

		boolean wasHidden() {
			return wasHidden;
		}
	}
}
