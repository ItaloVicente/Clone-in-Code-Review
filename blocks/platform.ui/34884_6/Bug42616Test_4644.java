
package org.eclipse.ui.tests.api;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.tests.harness.util.FileUtil;
import org.eclipse.ui.tests.harness.util.UITestCase;
import org.junit.Test;

public class Bug407422Test extends UITestCase {

	public Bug407422Test(String testName) {
		super(testName);
	}

	@Test
	public void test() throws CoreException {
		final IWorkbenchWindow window = openTestWindow();
		final IWorkbenchPage page = window.getActivePage();
		final String EDITOR_ID = "org.eclipse.ui.DefaultTextEditor";

		final IProject project = FileUtil.createProject("Bug407422Test");
		final IFile file1 = FileUtil.createFile("file1.txt", project);
		final IFile file2 = FileUtil.createFile("file2.txt", project);
		final IFile file3 = FileUtil.createFile("file3.txt", project);

		final FileEditorInput input1 = new FileEditorInput(file1);
		final FileEditorInput input2 = new FileEditorInput(file2);
		final FileEditorInput input3 = new FileEditorInput(file3);

		final List<IWorkbenchPartReference> openedParts = new ArrayList<IWorkbenchPartReference>();

		page.addPartListener(new IPartListener2() {

			@Override
			public void partVisible(IWorkbenchPartReference partRef) { }

			@Override
			public void partOpened(IWorkbenchPartReference partRef) {
				openedParts.add(partRef);
			}

			@Override
			public void partInputChanged(IWorkbenchPartReference partRef) {}

			@Override
			public void partHidden(IWorkbenchPartReference partRef) {}

			@Override
			public void partDeactivated(IWorkbenchPartReference partRef) {}

			@Override
			public void partClosed(IWorkbenchPartReference partRef) {
				openedParts.remove(partRef);
			}

			@Override
			public void partBroughtToTop(IWorkbenchPartReference partRef) { }

			@Override
			public void partActivated(IWorkbenchPartReference partRef) {}
		});

		final IEditorPart[] editorParts0 = page.getEditors();
		assertEquals(0, editorParts0.length);

		final IEditorReference[] openRefs = page.openEditors(new IEditorInput[] {input1, input2, input3},
				new String[] {EDITOR_ID,EDITOR_ID,EDITOR_ID}, 0);
		assertEquals(3, openRefs.length);

		assertEquals(1, openedParts.size());

		page.closeAllEditors(true);
		final IEditorReference[] editorReferences2 = page.getEditorReferences();
		assertEquals(0, editorReferences2.length);
		assertEquals(0, openedParts.size());

		final IEditorPart[] editorParts = page.getEditors();
		assertEquals(0, editorParts.length);






	}

}
