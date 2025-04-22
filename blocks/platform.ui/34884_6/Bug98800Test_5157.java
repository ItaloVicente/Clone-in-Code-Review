package org.eclipse.ui.tests.session;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MPlaceholder;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.EditorSite;
import org.eclipse.ui.tests.api.SessionEditorPart;
import org.eclipse.ui.tests.dnd.DragOperations;
import org.eclipse.ui.tests.dnd.EditorDropTarget;
import org.eclipse.ui.tests.dnd.ExistingWindowProvider;
import org.eclipse.ui.tests.harness.util.FileUtil;

public class Bug95357Test extends TestCase {
	public static TestSuite suite() {
		TestSuite ts = new TestSuite("org.eclipse.ui.tests.session.Bug95357Test");
		ts.addTest(new Bug95357Test("testMultipleEditors"));
		ts.addTest(new Bug95357Test("testMultipleEditorsOpen"));
		ts.addTest(new Bug95357Test("testMultipleStacks"));
		ts.addTest(new Bug95357Test("testMultipleStacksOnStartup"));
		ts.addTest(new Bug95357Test("testMultipleEditorsSession"));
		ts.addTest(new Bug95357Test("testMultipleEditorsOpenSession"));
		ts.addTest(new Bug95357Test("testMultipleStacksSession"));
		ts.addTest(new Bug95357Test("testMultipleStacksOnStartupSession"));
		return ts;
	}

	private static final String BUG95357PROJECT = "Bug95357project";

	private static final int FILE_MAX = 8;

	private IWorkbenchWindow fWin;

	private IWorkbenchPage fActivePage;

	private IProject fProject;

	private IWorkbench fWorkbench;

	private String[] itsFilename;

	public Bug95357Test(String testName) {
		super(testName);
		fWorkbench = PlatformUI.getWorkbench();
		fProject = null;

	}

	private void setupFilenames(String ext) {
		itsFilename = new String[Bug95357Test.FILE_MAX];
		for (int i = 0; i < Bug95357Test.FILE_MAX; ++i) {
			itsFilename[i] = "test" + i + ext;
		}
	}

	@Override
	protected void setUp() throws Exception {
		fWin = fWorkbench.getActiveWorkbenchWindow();

		fActivePage = fWin.getActivePage();
		fProject = FileUtil.createProject(Bug95357Test.BUG95357PROJECT);
	}

	private void multipleEditors() throws PartInitException, CoreException {
		fActivePage.closeAllEditors(false);
		
		IEditorPart[] part = new IEditorPart[itsFilename.length];
		for (int i = 0; i < itsFilename.length; i++) {
			part[i] = IDE.openEditor(fActivePage, FileUtil.createFile(
					itsFilename[i], fProject), true);
		}

		assertTrue(fActivePage.isEditorAreaVisible());
		assertFalse(fActivePage.isPartVisible(part[0]));
		assertTrue(fActivePage.isPartVisible(part[part.length - 1]));
	}

	private void multipleEditorsOpen() {
		IEditorReference[] editors = fActivePage.getEditorReferences();
		assertEquals(Bug95357Test.FILE_MAX, editors.length);

		for (int i = 0; i < editors.length - 1; i++) {
			assertNull("Editor " + i + " " + editors[i].getName()
					+ " should not be active", editors[i].getEditor(false));
		}
		assertNotNull(editors[editors.length - 1].getEditor(false));
		assertNotNull(editors[0].getEditor(true));
	}

	private void multipleStacks() throws PartInitException, CoreException {
		final String f1 = itsFilename[0];
		final String f2 = itsFilename[1];
		final int startAt = 2;

		fActivePage.closeAllEditors(false);

		IEditorPart last = IDE.openEditor(fActivePage, FileUtil.createFile(f1,
				fProject), true);
		IEditorPart current = IDE.openEditor(fActivePage, FileUtil.createFile(
				f2, fProject), true);

		DragOperations.drag(current, new EditorDropTarget(
				new ExistingWindowProvider(fWin), 0, SWT.BOTTOM), false);

		MElementContainer<MUIElement> firstStack = getParent(((EditorSite) last.getEditorSite()).getModel());
		MElementContainer<MUIElement> secondStack = getParent(((EditorSite) current.getEditorSite()).getModel());

		for (int i = startAt; i < itsFilename.length; ++i) {
			fActivePage.activate(last);
			last = current;
			current = IDE.openEditor(fActivePage, FileUtil.createFile(
					itsFilename[i], fProject), true);
		}
		assertEquals(Bug95357Test.FILE_MAX / 2, firstStack.getChildren().size());
		assertEquals(Bug95357Test.FILE_MAX / 2, secondStack.getChildren().size());
	}

	private MElementContainer<MUIElement> getParent(MUIElement element) {
		MElementContainer<MUIElement> parent = element.getParent();
		if (parent != null)
			return parent;
		MPlaceholder placeholder = element.getCurSharedRef();
		if (placeholder != null)
			return placeholder.getParent();
		return null;
	}
	
	private void multipleStacksOnStartup() {
		IEditorReference lastFile = null;
		IEditorReference secondLastFile = null;

		IEditorReference[] editors = fActivePage.getEditorReferences();
		assertEquals(Bug95357Test.FILE_MAX, editors.length);

		for (int i = 0; i < editors.length; i++) {
			if (itsFilename[itsFilename.length - 1]
					.equals(editors[i].getName())) {
				lastFile = editors[i];
			} else if (itsFilename[itsFilename.length - 2].equals(editors[i]
					.getName())) {
				secondLastFile = editors[i];
			}
		}

		assertNotNull(lastFile.getEditor(false));
		assertNotNull(secondLastFile.getEditor(false));
		for (int i = 0; i < editors.length; ++i) {
			if (editors[i] != lastFile && editors[i] != secondLastFile) {
				assertNull("For file " + i + " " + editors[i].getName(),
						editors[i].getEditor(false));
			}
		}
	}

	public void testMultipleEditors() throws Throwable {
		setupFilenames(".txt");
		
		multipleEditors();
	}

	public void testMultipleEditorsOpen() throws Throwable {
		setupFilenames(".txt");
	
		multipleEditorsOpen();
	}

	public void testMultipleStacks() throws Throwable {
		setupFilenames(".txt");
		multipleStacks();
	}

	public void testMultipleStacksOnStartup() throws Throwable {
		setupFilenames(".txt");
		multipleStacksOnStartup();
		
	}
	
	public void testMultipleEditorsSession() throws Throwable {
		setupFilenames(".session");
		multipleEditors();
		assertEquals(Bug95357Test.FILE_MAX, SessionEditorPart.instantiatedEditors);
		
	}
	
	public void testMultipleEditorsOpenSession() throws Throwable {
		setupFilenames(".session");
		multipleEditorsOpen();
		assertEquals(2, SessionEditorPart.instantiatedEditors);
	}

	public void testMultipleStacksSession() throws Throwable {
		setupFilenames(".session");
		SessionEditorPart.instantiatedEditors = 0;
		multipleStacks();
		assertEquals(Bug95357Test.FILE_MAX, SessionEditorPart.instantiatedEditors);
		
	}
	
	public void testMultipleStacksOnStartupSession() throws Throwable {
		setupFilenames(".session");
		multipleStacksOnStartup();
		assertEquals(2, SessionEditorPart.instantiatedEditors);
	}
}
