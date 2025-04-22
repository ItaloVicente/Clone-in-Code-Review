package org.eclipse.ui.tests.api.workbenchpart;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.tests.harness.util.FileUtil;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class Bug543609Test extends UITestCase {

	private IWorkbenchPage fPage;

	public Bug543609Test(String testName) {
		super(testName);
	}

	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();
		IWorkbenchWindow window = openTestWindow();
		fPage = window.getActivePage();
	}

	public void testViewWithState() throws Exception {
		ViewWithState view = (ViewWithState) fPage.showView(ViewWithState.ID);
		int savedState = ++view.fState;
		fPage.hideView(view);
		ViewWithState view2 = (ViewWithState) fPage.showView(ViewWithState.ID);
		assertNotSame(view, view2);
		assertEquals(savedState, view2.fState);
	}

	public void testEditorWithState() throws Exception {
		IProject proj = FileUtil.createProject("Bug543609Test");
		IFile file = FileUtil.createFile("Bug543609Test.txt", proj);
		EditorWithState editor = (EditorWithState) fPage.openEditor(
				new FileEditorInput(file), EditorWithState.ID);
		int savedState = ++editor.fState;
		fPage.closeEditor(editor, false);
		EditorWithState editor2 = (EditorWithState) fPage.openEditor(
				new FileEditorInput(file), EditorWithState.ID);
		assertNotSame(editor, editor2);
		assertEquals(savedState, editor2.fState);
	}
}
