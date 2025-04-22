package org.eclipse.ui.tests.session;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart3;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.tests.api.workbenchpart.TitleTestEditor;
import org.eclipse.ui.tests.harness.util.FileUtil;

public class ArbitraryPropertiesEditorTest extends TestCase {
	private static final String USER_PROP = "org.eclipse.ui.tests.users";

	private static final String EDITOR_ID = "org.eclipse.ui.tests.TitleTestEditor";

	public static TestSuite suite() {
		TestSuite ts = new TestSuite("org.eclipse.ui.tests.session.ArbitraryPropertiesEditorTest");
		ts.addTest(new ArbitraryPropertiesEditorTest("testOpenEditor"));
		ts.addTest(new ArbitraryPropertiesEditorTest("testSecondOpening"));
		ts.addTest(new ArbitraryPropertiesEditorTest("testPartInstantiation"));
		return ts;
	}

	public ArbitraryPropertiesEditorTest(String testName) {
		super(testName);
	}

	public void testOpenEditor() throws Throwable {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final IWorkbenchPage page = workbench.getActiveWorkbenchWindow()
				.getActivePage();

		IProject proj = FileUtil.createProject("EditorSessionTest");
		IFile file = FileUtil.createFile("state.txt", proj);

		TitleTestEditor editor = (TitleTestEditor) page.openEditor(
				new FileEditorInput(file), EDITOR_ID);

		file = FileUtil.createFile("state_active.txt", proj);
		IDE.openEditor(page, file);

		editor.setPartProperty(USER_PROP, "pwebster");
	}

	public void testSecondOpening() throws Throwable {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final IWorkbenchPage page = workbench.getActiveWorkbenchWindow()
				.getActivePage();
		IEditorReference[] editors = page.getEditorReferences();
		for (int i = 0; i < editors.length; i++) {
			IEditorReference ref = editors[i];
			if (ref.getEditorInput().getName().equals("state.txt")) {
				assertNull("The editor should not be instantiated", ref
						.getPart(false));
				assertEquals("pwebster", ref.getPartProperty(USER_PROP));
			}
		}
	}

	static class PropListener implements IPropertyChangeListener {
		public int count = 0;

		@Override
		public void propertyChange(PropertyChangeEvent event) {
			count++;
		}
	};

	public void testPartInstantiation() throws Throwable {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final IWorkbenchPage page = workbench.getActiveWorkbenchWindow()
				.getActivePage();

		IEditorReference ref = null;
		IEditorReference[] editors = page.getEditorReferences();
		for (int i = 0; i < editors.length; i++) {
			if (editors[i].getEditorInput().getName().equals("state.txt")) {
				ref = editors[i];
			}
		}

		assertEquals("pwebster", ref.getPartProperty(USER_PROP));
		PropListener listener = new PropListener();
		ref.addPartPropertyListener(listener);

		try {

			IWorkbenchPart3 wp = (IWorkbenchPart3) ref.getPart(true);
			assertEquals("pwebster", wp.getPartProperty(USER_PROP));
			assertEquals(0, listener.count);
		} finally {
			ref.removePartPropertyListener(listener);
		}

		IEditorInput input = ref.getEditorInput();

		page.closeEditor((IEditorPart) ref.getPart(true), false);

		TitleTestEditor editor = (TitleTestEditor) page.openEditor(input,
				EDITOR_ID);
		assertNull(editor.getPartProperty(USER_PROP));
	}
}
