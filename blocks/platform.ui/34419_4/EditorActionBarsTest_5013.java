package org.eclipse.ui.tests.internal;

import java.io.ByteArrayInputStream;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.DeleteResourceAction;
import org.eclipse.ui.actions.TextActionHandler;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.operations.AdvancedValidationUserApprover;
import org.eclipse.ui.views.navigator.ResourceNavigator;

public class Bug99858Test extends TestCase {

	private static final String NAVIGATOR_VIEW = "org.eclipse.ui.views.ResourceNavigator";

	public static TestSuite suite() {
		return new TestSuite(Bug99858Test.class);
	}

	public Bug99858Test() {
		super();
	}

	public Bug99858Test(String name) {
		super(name);
	}

	public void testDeleteClosedProject() throws Throwable {
		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject testProject = workspace.getRoot().getProject(
				"TestClosedDelete");
		testProject.create(null);
		testProject.open(null);

		String contents = "File ready for execution, sir!";
		createProjectFile(testProject, "a.txt", contents);
		createProjectFile(testProject, "b.txt", contents);

		ResourceNavigator view = (ResourceNavigator) page
				.showView(NAVIGATOR_VIEW);
		view.setFocus();

		MyDeleteResourceAction newDel = new MyDeleteResourceAction(
				view.getViewSite());
		newDel.setEnabled(true);
		TextActionHandler tmpHandler = new TextActionHandler(view.getViewSite()
				.getActionBars());
		tmpHandler.setDeleteAction(newDel);

		view.getViewSite().getActionBars().updateActionBars();

		chewUpEvents();

		StructuredSelection s = new StructuredSelection(testProject);

		testProject.close(null);
		assertFalse(testProject.isAccessible());
		view.getViewSite().getSelectionProvider().setSelection(s);
		newDel.selectionChanged(s);
		chewUpEvents();

		IAction del = view.getViewSite().getActionBars()
				.getGlobalActionHandler(ActionFactory.DELETE.getId());

		assertTrue(del.isEnabled());

		del.runWithEvent(null);

		chewUpEvents();

		assertTrue(newDel.fRan);

		boolean joined = false;
		while (!joined) {
			try {
				Platform.getJobManager()
						.join(IDEWorkbenchMessages.DeleteResourceAction_jobName,
								null);
				joined = true;
			} catch (InterruptedException ex) {
				chewUpEvents();
			}
		}

		joined = false;
		while (!joined) {
			try {
				Platform.getJobManager()
						.join(IDEWorkbenchMessages.DeleteResourceAction_jobName,
								null);
				joined = true;
			} catch (InterruptedException ex) {
				chewUpEvents();
			}
		}

		assertFalse(testProject.exists());
	}

	private class MyDeleteResourceAction extends DeleteResourceAction {

		public boolean fRan = false;

		public MyDeleteResourceAction(IShellProvider provider) {
			super(provider);
			fTestingMode = true;
		}

		@Override
		public void run() {
			super.run();
			fRan = true;
		}
	}

	private void createProjectFile(IProject testProject, String name,
			String contents) throws CoreException {
		IFile textFile = testProject.getFile(name);
		ByteArrayInputStream inputStream = new ByteArrayInputStream(
				contents.getBytes());
		textFile.create(inputStream, true, null);
	}

	private void chewUpEvents() {
		Display display = Display.getCurrent();
		while (display.readAndDispatch()) {
			;
		}
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		AdvancedValidationUserApprover.AUTOMATED_MODE = true;
	}

	@Override
	protected void tearDown() throws Exception {
		AdvancedValidationUserApprover.AUTOMATED_MODE = false;
		super.tearDown();
	}
}
