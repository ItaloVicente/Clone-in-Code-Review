package org.eclipse.ui.tests.multieditor;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import junit.framework.TestSuite;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IActionBars2;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.internal.WorkbenchPage;
import org.eclipse.ui.internal.WorkbenchPartReference;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.IContributedContentsView;
import org.eclipse.ui.part.MultiEditor;
import org.eclipse.ui.part.MultiEditorInput;
import org.eclipse.ui.tests.TestPlugin;
import org.eclipse.ui.tests.api.MockEditorPart;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class MultiEditorTest extends UITestCase {
	private static final String ACTION_TOOLTIP = "MultiEditorActionThing";

	private static final String PROJECT_NAME = "TiledEditorProject";

	private static final String CONTENT_OUTLINE = "org.eclipse.ui.views.ContentOutline";

	private static final String TESTEDITOR_COOLBAR = "org.eclipse.ui.tests.multieditor.actionSet";

	private static final String TILED_EDITOR_ID = "org.eclipse.ui.tests.multieditor.TiledEditor";

	private static final String DATA_FILES_DIR = "/data/org.eclipse.newMultiEditor/";

	private static final String TEST01_TXT = "test01.txt";

	private static final String TEST02_TXT = "test02.txt";

	private static final String TEST03_ETEST = "test03.etest";

	private static final String TEST04_PROPERTIES = "test04.properties";

	private static final String BUILD_XML = "build.xml";

	private static String[] gEditorOpenTrace = { "setInitializationData",
			"init", "createPartControl", "createInnerPartControl",
			"createInnerPartControl", "setFocus", "updateGradient",
			"updateGradient", };

	private static String[] gEditorFocusTrace = { "setInitializationData",
			"init", "createPartControl", "createInnerPartControl",
			"createInnerPartControl", "setFocus", "updateGradient",
			"updateGradient", "updateGradient", "updateGradient", };

	private static String[] gEditorCloseTrace = { "setInitializationData",
			"init", "createPartControl", "createInnerPartControl",
			"createInnerPartControl", "setFocus", "updateGradient",
			"updateGradient", "updateGradient", "updateGradient",
			"widgetsDisposed", "dispose" };

	public static TestSuite suite() {
		return new TestSuite(MultiEditorTest.class);
	}

	private EditorErrorListener fErrorListener;

	public MultiEditorTest(String tc) {
		super(tc);
	}

	public void testOpenBasicEditor() throws Throwable {
		final String[] simpleFiles = { TEST01_TXT, TEST02_TXT };

		IWorkbenchWindow window = openTestWindow();
		IWorkbenchPage page = window.getActivePage();

		IProject testProject = findOrCreateProject(PROJECT_NAME);

		MultiEditorInput input = generateEditorInput(simpleFiles, testProject);

		openAndValidateEditor(page, input);
	}

	public void testOpenTestFile() throws Throwable {
		final String[] simpleFiles = { TEST01_TXT, TEST03_ETEST };

		IWorkbenchWindow window = openTestWindow();
		WorkbenchPage page = (WorkbenchPage) window.getActivePage();

		IProject testProject = findOrCreateProject(PROJECT_NAME);

		MultiEditorInput input = generateEditorInput(simpleFiles, testProject);

		IEditorPart editor = openAndValidateEditor(page, input);

		assertTrue(editor instanceof MultiEditor);
		MultiEditor multiEditor = (MultiEditor) editor;

		chewUpEvents();


		assertTrue("The editor open trace was incorrect",
				((TiledEditor) multiEditor).callHistory
						.verifyOrder(gEditorOpenTrace));

		IEditorPart[] innerEditors = multiEditor.getInnerEditors();
		innerEditors[innerEditors.length - 1].setFocus();

		chewUpEvents();

		assertTrue("Editor setFocus trace was incorrect",
				((TiledEditor) multiEditor).callHistory
						.verifyOrder(gEditorFocusTrace));

		page.closeEditor(multiEditor, false);

		chewUpEvents();

		assertTrue("Editor close trace was incorrect",
				((TiledEditor) multiEditor).callHistory
						.verifyOrder(gEditorCloseTrace));
	}
	public void testDirty() throws Throwable {
		IWorkbenchWindow window = openTestWindow();
		WorkbenchPage page = (WorkbenchPage) window.getActivePage();
		page.closeAllEditors(false);

		IProject testProject = findOrCreateProject(PROJECT_NAME);

		IFile fileA = createFile(testProject, TEST01_TXT);
		IFile fileB = createFile(testProject, TEST02_TXT);
		
		MultiEditorInput input = new MultiEditorInput(new String[] { MockEditorPart.ID1, MockEditorPart.ID1}, new IEditorInput[] { new FileEditorInput(fileA),new FileEditorInput(fileB)});

		IEditorPart editor = openAndValidateEditor(page, input);

		assertTrue(editor instanceof MultiEditor);
		MultiEditor multiEditor = (MultiEditor) editor;
		
		CTabFolder tabFolder = (CTabFolder) ((WorkbenchPartReference) page
				.getReference(multiEditor)).getModel().getParent().getWidget();
		CTabItem item = tabFolder.getItem(0);

		IEditorPart[] innerEditors = multiEditor.getInnerEditors();
		MockEditorPart editorA = (MockEditorPart) innerEditors[0];
		MockEditorPart editorB = (MockEditorPart) innerEditors[0];
		
		char firstChar = item.getText().charAt(0);
		assertFalse(firstChar == '*');
		
		try {
			editorA.setDirty(true);
			assertEquals('*', item.getText().charAt(0));
			
			editorA.setDirty(false);
			assertEquals(firstChar, item.getText().charAt(0));

			editorB.setDirty(true);
			assertEquals('*', item.getText().charAt(0));

			editorB.setDirty(false);
			assertEquals(firstChar, item.getText().charAt(0));
		} finally {
			page.closeAllEditors(false);
		}
	}

	public void testTrackCoolBar() throws Throwable {
		final String[] simpleFiles = { TEST01_TXT, TEST02_TXT,
				TEST04_PROPERTIES, BUILD_XML, TEST03_ETEST };

		IWorkbenchWindow window = openTestWindow();
		WorkbenchPage page = (WorkbenchPage) window.getActivePage();

		IProject testProject = findOrCreateProject(PROJECT_NAME);

		MultiEditorInput input = generateEditorInput(simpleFiles, testProject);

		IEditorPart editor = openAndValidateEditor(page, input);

		assertTrue(editor instanceof MultiEditor);
		MultiEditor multiEditor = (MultiEditor) editor;

		chewUpEvents();

		IContributionItem contribution = findMyCoolBar(page);

		validateIconState(contribution, ACTION_TOOLTIP, false);

		IEditorPart[] innerEditors = multiEditor.getInnerEditors();
		innerEditors[innerEditors.length - 1].setFocus();

		chewUpEvents();

		contribution = findMyCoolBar(page);
		assertNotNull("It should be available now", contribution);

		validateIconState(contribution, ACTION_TOOLTIP, true);

	}

	public void xtestTrackOutline() throws Throwable {
		final String[] simpleFiles = { TEST01_TXT, TEST02_TXT,
				TEST04_PROPERTIES, BUILD_XML, TEST03_ETEST };

		IWorkbenchWindow window = openTestWindow();
		WorkbenchPage page = (WorkbenchPage) window.getActivePage();

		IProject testProject = findOrCreateProject(PROJECT_NAME);

		MultiEditorInput input = generateEditorInput(simpleFiles, testProject);

		IEditorPart editor = openAndValidateEditor(page, input);

		assertTrue(editor instanceof MultiEditor);
		MultiEditor multiEditor = (MultiEditor) editor;

		chewUpEvents();

		IEditorPart[] innerEditors = multiEditor.getInnerEditors();
		innerEditors[innerEditors.length - 2].setFocus();
		chewUpEvents();

		IViewPart outline = window.getActivePage().showView(CONTENT_OUTLINE);
		assertNotNull(outline);

		IContributedContentsView view = (IContributedContentsView) outline
				.getAdapter(IContributedContentsView.class);
		IWorkbenchPart part = view.getContributingPart();
		assertNotNull("The Outline view has not been updated by the editor",
				part);
		assertTrue("The Outline view is not talking to an editor",
				part instanceof IEditorPart);

		IEditorPart outlineEditor = (IEditorPart) part;

		assertEquals("The Outline view is not talking to the correct editor",
				multiEditor.getActiveEditor(), outlineEditor);

		page.closeEditor(editor, false);
		chewUpEvents();

		view = (IContributedContentsView) outline
				.getAdapter(IContributedContentsView.class);
		assertNull(view.getContributingPart());
	}

	private IContributionItem findMyCoolBar(WorkbenchPage page) {
		IContributionItem contribution = ((IActionBars2) page.getActionBars())
				.getCoolBarManager().find(TESTEDITOR_COOLBAR);

		return contribution;
	}

	private void validateIconState(IContributionItem contribution,
			String tooltip, boolean state) {
		assertTrue("We might not have the contribution or expect it",
				contribution != null || !state);
		if (contribution == null) {
			return;
		}

		ToolBarManager toolBarManager = (ToolBarManager) ((ToolBarContributionItem) contribution)
				.getToolBarManager();
		ToolBar bar = toolBarManager.getControl();

		assertTrue("It's OK for bar to be null if we expect state to be false",
				bar != null || !state);
		if (bar == null) {
			return;
		}

		ToolItem[] items = bar.getItems();
		for (int i = 0; i < items.length; ++i) {
			if (tooltip.equals(items[i].getToolTipText())) {
				assertEquals("Invalid icon state for " + tooltip, state,
						items[i].getEnabled());
				return;
			}
		}
		assertFalse("We haven't found our item", state);
	}

	private IProject findOrCreateProject(String projectName)
			throws CoreException {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject testProject = workspace.getRoot().getProject(projectName);
		if (!testProject.exists()) {
			testProject.create(null);
		}
		testProject.open(null);
		return testProject;
	}

	private void chewUpEvents() throws InterruptedException {
		Thread.sleep(500);
		Display display = Display.getCurrent();
		while (display.readAndDispatch())
			;
	}

	private IEditorPart openAndValidateEditor(IWorkbenchPage page,
			MultiEditorInput input) throws PartInitException {

		IEditorPart editorPart = null;
		try {
			setupErrorListener();
			editorPart = page
					.openEditor(input, MultiEditorTest.TILED_EDITOR_ID);
			assertNotNull(editorPart);


			if (fErrorListener.messages.size() > 0) {
				String[] msgs = (String[]) fErrorListener.messages
						.toArray(new String[fErrorListener.messages.size()]);
				for (int i = 0; i < msgs.length; i++) {
					if (msgs[i].indexOf("The proxied handler for") == -1
							&& msgs[i].indexOf("Conflict for \'") == -1
							&& msgs[i].indexOf("Keybinding conflicts occurred")==-1
							&& msgs[i].indexOf("A handler conflict occurred")==-1) {
						fail("Failed with: " + msgs[i]);
					}
				}
			}
		} finally {
			removeErrorListener();
		}
		return editorPart;
	}

	private void setupErrorListener() {
		final ILog log = WorkbenchPlugin.getDefault().getLog();
		fErrorListener = new EditorErrorListener();
		log.addLogListener(fErrorListener);
	}

	private void removeErrorListener() {
		final ILog log = WorkbenchPlugin.getDefault().getLog();
		if (fErrorListener != null) {
			log.removeLogListener(fErrorListener);
			fErrorListener = null;
		}
	}

	private MultiEditorInput generateEditorInput(String[] simpleFiles,
			IProject testProject) throws CoreException, IOException {
		String[] ids = new String[simpleFiles.length];
		IEditorInput[] inputs = new IEditorInput[simpleFiles.length];
		IEditorRegistry registry = fWorkbench.getEditorRegistry();

		for (int f = 0; f < simpleFiles.length; ++f) {
			IFile f1 = createFile(testProject, simpleFiles[f]);
			ids[f] = registry.getDefaultEditor(f1.getName()).getId();
			inputs[f] = new FileEditorInput(f1);
		}

		MultiEditorInput input = new MultiEditorInput(ids, inputs);
		return input;
	}
	
	private IFile createFile(IProject testProject, String simpleFile) throws CoreException, IOException {
		IFile file = testProject.getFile(simpleFile);
		if (!file.exists()) {
			URL url = Platform.asLocalURL(TestPlugin.getDefault()
					.getBundle().getEntry(DATA_FILES_DIR + simpleFile));
			file.create(url.openStream(), true, null);
		}
		return file;
	}

	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();
		IWorkbenchPage page = fWorkbench.getActiveWorkbenchWindow()
				.getActivePage();
		page.closeAllEditors(false);

	}

	public static class EditorErrorListener implements ILogListener {

		public ArrayList messages = new ArrayList();

		@Override
		public void logging(IStatus status, String plugin) {
			String msg = status.getMessage();
			Throwable ex = status.getException();
			if (ex != null) {
				msg += ": " + ex.getMessage();
			}
			messages.add(msg);
		}
	}
}
