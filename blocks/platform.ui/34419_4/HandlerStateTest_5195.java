package org.eclipse.ui.tests.session;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.tests.api.MockEditorWithState;
import org.eclipse.ui.tests.harness.util.FileUtil;

public class EditorWithStateTest extends TestCase {

	public static TestSuite suite() {
		TestSuite suite = new TestSuite("org.eclipse.ui.tests.session.EditorWithStateTest");
		suite.addTest(new EditorWithStateTest("testInitialEditorOpen"));
		suite.addTest(new EditorWithStateTest("testSecondEditorOpen"));
		return suite;
	}

	public EditorWithStateTest(final String testName) {
		super(testName);
	}

	public void testInitialEditorOpen() throws Exception {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final IWorkbenchPage page = workbench.getActiveWorkbenchWindow()
				.getActivePage();
		IProject proj = FileUtil.createProject("EditorSessionTest");
		IFile file = FileUtil.createFile("state.txt", proj);
		MockEditorWithState editor = (MockEditorWithState) page.openEditor(
				new FileEditorInput(file), MockEditorWithState.ID);
		assertFalse(editor.getCallHistory().contains("saveState"));
		assertFalse(editor.getCallHistory().contains("restoreState"));

		assertTrue(editor.isSaveOnCloseNeeded());
		editor.setSaveNeeded(false);
	}

	public void testSecondEditorOpen() throws Exception {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final IWorkbenchPage page = workbench.getActiveWorkbenchWindow()
				.getActivePage();
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				"EditorSessionTest");
		IFile file = project.getFile("state.txt");
		MockEditorWithState editor = (MockEditorWithState) page
				.findEditor(new FileEditorInput(file));
		assertNotNull(editor);
		assertFalse(editor.getCallHistory().contains("saveState"));
		assertTrue(editor.getCallHistory().contains("restoreState"));
		assertFalse(editor.isSaveOnCloseNeeded());
	}
}
