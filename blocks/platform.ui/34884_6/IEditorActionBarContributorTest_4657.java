package org.eclipse.ui.tests.api;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.tests.harness.util.ArrayUtil;
import org.eclipse.ui.tests.harness.util.CallHistory;
import org.eclipse.ui.tests.harness.util.EmptyPerspective;
import org.eclipse.ui.tests.harness.util.FileUtil;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class IDeprecatedWorkbenchPageTest extends UITestCase {

	private IWorkbenchPage fActivePage;

	private IWorkbenchWindow fWin;

	private IProject proj;
	
	public IDeprecatedWorkbenchPageTest(String testName) {
		super(testName);
	}

	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();
		fWin = openTestWindow();
		fActivePage = fWin.getActivePage();
	}

	@Override
	protected void doTearDown() throws Exception {
		super.doTearDown();
		if (proj != null) {
			FileUtil.deleteProject(proj);
			proj = null;
		}
	}

	public void testGet_SetEditorAreaVisible() throws Throwable {
		fActivePage.setEditorAreaVisible(true);
		assertTrue(fActivePage.isEditorAreaVisible() == true);

		fActivePage.setEditorAreaVisible(false);
		assertTrue(fActivePage.isEditorAreaVisible() == false);
	}

	public void testGetPerspective() throws Throwable {
		assertNotNull(fActivePage.getPerspective());

		IWorkbenchPage page = fWin.openPage(EmptyPerspective.PERSP_ID,
				getPageInput());
		assertEquals(EmptyPerspective.PERSP_ID, page.getPerspective().getId());
	}

	public void testSetPerspective() throws Throwable {
		IPerspectiveDescriptor per = PlatformUI.getWorkbench()
				.getPerspectiveRegistry().findPerspectiveWithId(
						EmptyPerspective.PERSP_ID);
		fActivePage.setPerspective(per);
		assertEquals(per, fActivePage.getPerspective());
	}

	public void testGetLabel() {
		assertNotNull(fActivePage.getLabel());
	}

	public void testGetInput() throws Throwable {
		IAdaptable input = getPageInput();
		IWorkbenchPage page = fWin.openPage(input);
		assertEquals(input, page.getInput());
	}

	public void testActivate() throws Throwable {
		MockViewPart part = (MockViewPart) fActivePage
				.showView(MockViewPart.ID);
		MockViewPart part2 = (MockViewPart) fActivePage
				.showView(MockViewPart.ID2);

		MockPartListener listener = new MockPartListener();
		fActivePage.addPartListener(listener);
		fActivePage.activate(part);

		CallHistory callTrace;

		callTrace = part2.getCallHistory();
		callTrace.clear();
		fActivePage.activate(part2);
		assertTrue(callTrace.contains("setFocus"));
		assertTrue(listener.getCallHistory().contains("partActivated"));

		callTrace = part.getCallHistory();
		callTrace.clear();
		fActivePage.activate(part);
		assertTrue(callTrace.contains("setFocus"));
		assertTrue(listener.getCallHistory().contains("partActivated"));
	}

	public void testBringToTop() throws Throwable {
		proj = FileUtil.createProject("testOpenEditor");
		IEditorPart part = IDE.openEditor(fActivePage, FileUtil.createFile(
				"a.mock1", proj), true);
		IEditorPart part2 = IDE.openEditor(fActivePage, FileUtil.createFile(
				"b.mock1", proj), true);

		MockPartListener listener = new MockPartListener();
		fActivePage.addPartListener(listener);
		CallHistory callTrace = listener.getCallHistory();

		fActivePage.bringToTop(part);
		assertEquals(callTrace.contains("partBroughtToTop"), true);

		callTrace.clear();
		fActivePage.bringToTop(part2);
		assertEquals(callTrace.contains("partBroughtToTop"), true);
	}

	public void testGetWorkbenchWindow() {
	}

	public void testShowView() throws Throwable {
		MockViewPart view = (MockViewPart) fActivePage
				.showView(MockViewPart.ID);
		assertNotNull(view);
		assertTrue(view.getCallHistory().verifyOrder(
				new String[] { "init", "createPartControl", "setFocus" }));

		fActivePage.showView(MockViewPart.ID2);

		CallHistory callTrace = view.getCallHistory();
		callTrace.clear();
		assertEquals(fActivePage.showView(MockViewPart.ID), view);
		assertEquals(callTrace.contains("setFocus"), true);
	}

	public void testOpenEditor() throws Throwable {
		proj = FileUtil.createProject("testOpenEditor");

		IFile file = FileUtil.createFile("test.mock1", proj);
		IEditorPart editor = IDE.openEditor(fActivePage, file, true);
		assertEquals(ArrayUtil.contains(fActivePage.getEditors(), editor), true);
		assertEquals(fActivePage.getActiveEditor(), editor);
		assertEquals(editor.getSite().getId(), fWorkbench.getEditorRegistry()
				.getDefaultEditor(file.getName()).getId());

		file = FileUtil.createFile("a.null and void", proj);
		editor = IDE.openEditor(fActivePage, file, true);
		if (editor != null) {//Editor may be external
			assertEquals(ArrayUtil.contains(fActivePage.getEditors(), editor),
					true);
			assertEquals(fActivePage.getActiveEditor(), editor);
			assertEquals(editor.getSite().getId(),
					"org.eclipse.ui.DefaultTextEditor");

			IDE.openEditor(fActivePage,
					FileUtil.createFile("test.mock2", proj), true);

			assertEquals(editor, IDE.openEditor(fActivePage, file, true));
			assertEquals(editor, fActivePage.getActiveEditor());
		}
	}

	public void testOpenEditor2() throws Throwable {
		proj = FileUtil.createProject("testOpenEditor");
		final IFile file = FileUtil.createFile("asfasdasdf", proj);
		final String id = MockEditorPart.ID1;

		IEditorPart editor = fActivePage.openEditor(new FileEditorInput(file),
				id);
		assertEquals(editor.getSite().getId(), id);
		assertEquals(ArrayUtil.contains(fActivePage.getEditors(), editor), true);
		assertEquals(fActivePage.getActiveEditor(), editor);

		IDE.openEditor(fActivePage, FileUtil.createFile("test.mock2", proj),
				true);

		assertEquals(fActivePage.openEditor(new FileEditorInput(file), id),
				editor);
		assertEquals(fActivePage.getActiveEditor(), editor);
	}

	public void testOpenEditor3() throws Throwable {
		proj = FileUtil.createProject("testOpenEditor");
		final String id = MockEditorPart.ID1;
		IEditorInput input = new FileEditorInput(FileUtil.createFile(
				"test.mock1", proj));

		IEditorPart editor = fActivePage.openEditor(input, id);
		assertEquals(editor.getEditorInput(), input);
		assertEquals(editor.getSite().getId(), id);
		assertEquals(ArrayUtil.contains(fActivePage.getEditors(), editor), true);
		assertEquals(fActivePage.getActiveEditor(), editor);

		IDE.openEditor(fActivePage, FileUtil.createFile("test.mock2", proj),
				true);

		assertEquals(fActivePage.openEditor(input, id), editor);
		assertEquals(fActivePage.getActiveEditor(), editor);
	}

	public void testOpenEditor4() throws Throwable {
		proj = FileUtil.createProject("testOpenEditor");
		final String id = MockEditorPart.ID1;
		IEditorInput input = new FileEditorInput(FileUtil.createFile(
				"test.mock1", proj));
		MockPartListener listener = new MockPartListener();
		fActivePage.addPartListener(listener);
		CallHistory callTrace = listener.getCallHistory();

		IEditorPart editor = fActivePage.openEditor(input, id, true);
		assertEquals(editor.getEditorInput(), input);
		assertEquals(editor.getSite().getId(), id);
		assertEquals(ArrayUtil.contains(fActivePage.getEditors(), editor), true);
		assertEquals(fActivePage.getActiveEditor(), editor);
		assertEquals(callTrace.contains("partActivated"), true);

		IEditorPart extra = IDE.openEditor(fActivePage, FileUtil.createFile(
				"aaaaa", proj), true);

		fActivePage.closeEditor(editor, false);

		fActivePage.showView(IPageLayout.ID_PROBLEM_VIEW, null,
				IWorkbenchPage.VIEW_ACTIVATE);
		callTrace.clear();
		editor = fActivePage.openEditor(input, id, false);
		assertEquals(editor.getEditorInput(), input);
		assertEquals(editor.getSite().getId(), id);
		assertEquals(ArrayUtil.contains(fActivePage.getEditors(), editor), true);
		assertEquals(callTrace.contains("partActivated"), false);
		assertEquals(callTrace.contains("partBroughtToTop"), true);

		fActivePage.activate(extra);

		fActivePage.showView(IPageLayout.ID_PROBLEM_VIEW, null,
				IWorkbenchPage.VIEW_ACTIVATE);
		callTrace.clear();
		assertEquals(fActivePage.openEditor(input, id, false), editor);
		assertEquals(callTrace.contains("partBroughtToTop"), true);
		assertEquals(callTrace.contains("partActivated"), false);

		fActivePage.activate(extra);

		callTrace.clear();
		assertEquals(fActivePage.openEditor(input, id, true), editor);
		assertEquals(callTrace.contains("partBroughtToTop"), true);
		assertEquals(callTrace.contains("partActivated"), true);
	}

	public void testOpenEditor5() throws Throwable {
		proj = FileUtil.createProject("testOpenEditor");
		IMarker marker = FileUtil.createFile("aa.mock2", proj).createMarker(
				IMarker.TASK);
		CallHistory callTrace;

		IEditorPart editor = IDE.openEditor(fActivePage, marker, true);
		callTrace = ((MockEditorPart) editor).getCallHistory();
		assertEquals(editor.getSite().getId(), MockEditorPart.ID2);
		assertEquals(ArrayUtil.contains(fActivePage.getEditors(), editor), true);
		assertEquals(fActivePage.getActiveEditor(), editor);
		assertEquals(callTrace.contains("gotoMarker"), true);
		fActivePage.closeEditor(editor, false);

		marker.setAttribute(IWorkbenchPage.EDITOR_ID_ATTR, MockEditorPart.ID1);
		editor = IDE.openEditor(fActivePage, marker, true);
		callTrace = ((MockEditorPart) editor).getCallHistory();
		assertEquals(editor.getSite().getId(), MockEditorPart.ID1);
		assertEquals(ArrayUtil.contains(fActivePage.getEditors(), editor), true);
		assertEquals(fActivePage.getActiveEditor(), editor);
		assertEquals(callTrace.contains("gotoMarker"), true);

		callTrace.clear();
		assertEquals(IDE.openEditor(fActivePage, marker, true), editor);
		assertEquals(fActivePage.getActiveEditor(), editor);
		assertEquals(callTrace.contains("gotoMarker"), true);
		fActivePage.closeEditor(editor, false);
	}

	public void testOpenEditor6() throws Throwable {
		proj = FileUtil.createProject("testOpenEditor");
		IMarker marker = FileUtil.createFile("aa.mock2", proj).createMarker(
				IMarker.TASK);
		MockPartListener listener = new MockPartListener();
		fActivePage.addPartListener(listener);
		CallHistory listenerCall = listener.getCallHistory();
		CallHistory editorCall;

		IEditorPart extra = IDE.openEditor(fActivePage, FileUtil.createFile(
				"aaaaa", proj), true);

		IEditorPart editor = IDE.openEditor(fActivePage, marker, true);
		editorCall = ((MockEditorPart) editor).getCallHistory();
		assertEquals(editor.getSite().getId(), MockEditorPart.ID2);
		assertEquals(ArrayUtil.contains(fActivePage.getEditors(), editor), true);
		assertEquals(fActivePage.getActiveEditor(), editor);

		assertEquals(editorCall.contains("gotoMarker"), true);
		fActivePage.closeEditor(editor, false);

		fActivePage.activate(extra);

		fActivePage.showView(IPageLayout.ID_PROBLEM_VIEW, null,
				IWorkbenchPage.VIEW_ACTIVATE);

		listenerCall.clear();
		editor = IDE.openEditor(fActivePage, marker, false);
		editorCall = ((MockEditorPart) editor).getCallHistory();
		assertEquals(editor.getSite().getId(), MockEditorPart.ID2);
		assertEquals(ArrayUtil.contains(fActivePage.getEditors(), editor), true);
		assertEquals(listenerCall.contains("partBroughtToTop"), true);
		assertEquals(listenerCall.contains("partActivated"), false);
		assertEquals(editorCall.contains("gotoMarker"), true);
		fActivePage.closeEditor(editor, false);

		String id = MockEditorPart.ID1;
		marker.setAttribute(IWorkbenchPage.EDITOR_ID_ATTR, id);

		listenerCall.clear();

		editor = IDE.openEditor(fActivePage, marker, true);
		editorCall = ((MockEditorPart) editor).getCallHistory();
		assertEquals(editor.getSite().getId(), id);
		assertEquals(ArrayUtil.contains(fActivePage.getEditors(), editor), true);
		assertEquals(fActivePage.getActiveEditor(), editor);
		assertEquals(editorCall.contains("gotoMarker"), true);
		fActivePage.closeEditor(editor, false);

		fActivePage.activate(extra);

		fActivePage.showView(IPageLayout.ID_PROBLEM_VIEW, null,
				IWorkbenchPage.VIEW_ACTIVATE);

		listenerCall.clear();
		editor = IDE.openEditor(fActivePage, marker, false);
		editorCall = ((MockEditorPart) editor).getCallHistory();
		assertEquals(editor.getSite().getId(), id);
		assertEquals(ArrayUtil.contains(fActivePage.getEditors(), editor), true);
		assertEquals(editorCall.contains("gotoMarker"), true);
		assertEquals(listenerCall.contains("partActivated"), false);
		assertEquals(listenerCall.contains("partBroughtToTop"), true);

		fActivePage.activate(extra);

		fActivePage.showView(IPageLayout.ID_PROBLEM_VIEW, null,
				IWorkbenchPage.VIEW_ACTIVATE);
		listenerCall.clear();
		assertEquals(IDE.openEditor(fActivePage, marker, false), editor);
		assertEquals(listenerCall.contains("partBroughtToTop"), true);
		assertEquals(listenerCall.contains("partActivated"), false);

		fActivePage.activate(extra);

		listenerCall.clear();
		assertEquals(IDE.openEditor(fActivePage, marker, true), editor);
		assertEquals(editorCall.contains("gotoMarker"), true);
		assertEquals(listenerCall.contains("partBroughtToTop"), true);
		assertEquals(listenerCall.contains("partActivated"), true);
	}

	public void testFindView() throws Throwable {
		String id = MockViewPart.ID3;
		assertNull(fActivePage.findView(id));

		IViewPart view = fActivePage.showView(id);
		assertEquals(fActivePage.findView(id), view);

		fActivePage.hideView(view);
		assertNull(fActivePage.findView(id));
	}

	public void testGetViews() throws Throwable {
		int totalBefore = fActivePage.getViews().length;

		IViewPart view = fActivePage.showView(MockViewPart.ID2);
		assertEquals(ArrayUtil.contains(fActivePage.getViews(), view), true);
		assertEquals(fActivePage.getViews().length, totalBefore + 1);

		fActivePage.hideView(view);
		assertEquals(ArrayUtil.contains(fActivePage.getViews(), view), false);
		assertEquals(fActivePage.getViews().length, totalBefore);
	}

	public void testHideView() throws Throwable {
		IViewPart view = fActivePage.showView(MockViewPart.ID3);

		fActivePage.hideView(view);
		CallHistory callTrace = ((MockViewPart) view).getCallHistory();
		assertTrue(callTrace.contains("dispose"));
	}

	public void XXXtestClose() throws Throwable {
		IWorkbenchPage page = openTestPage(fWin);

		proj = FileUtil.createProject("testOpenEditor");
		final IFile file = FileUtil.createFile("aaa.mock1", proj);
		IEditorPart editor = IDE.openEditor(page, file, true);
		CallHistory callTrace = ((MockEditorPart) editor).getCallHistory();
		callTrace.clear();

		assertEquals(page.close(), true);
		assertEquals(callTrace
				.verifyOrder(new String[] { "isDirty", "dispose" }), true);
		assertEquals(fWin.getActivePage(), fActivePage);
	}

	public void testCloseEditor() throws Throwable {
		proj = FileUtil.createProject("testOpenEditor");
		final IFile file = FileUtil.createFile("test.mock1", proj);
		IEditorPart editor;
		CallHistory callTrace;
		MockEditorPart mock;

		editor = IDE.openEditor(fActivePage, file, true);
		mock = (MockEditorPart) editor;
		mock.setSaveNeeded(true);
		callTrace = mock.getCallHistory();
		callTrace.clear();
		assertEquals(fActivePage.closeEditor(editor, true), true);
		assertEquals(callTrace
				.verifyOrder(new String[] { "isDirty", "dispose" }), true);

		editor = IDE.openEditor(fActivePage, file, true);
		mock = (MockEditorPart) editor;
		mock.setDirty(true);
		mock.setSaveNeeded(true);
		callTrace = mock.getCallHistory();
		callTrace.clear();
		assertEquals(fActivePage.closeEditor(editor, false), true);
		assertEquals(callTrace.contains("isSaveOnCloseNeeded"), false);
		assertEquals(callTrace.contains("doSave"), false);
		assertEquals(callTrace.contains("dispose"), true);
	}

	public void testCloseAllEditors() throws Throwable {
		int total = 5;
		final IFile[] files = new IFile[total];
		IEditorPart[] editors = new IEditorPart[total];
		CallHistory[] callTraces = new CallHistory[total];
		MockEditorPart[] mocks = new MockEditorPart[total];

		proj = FileUtil.createProject("testOpenEditor");
		for (int i = 0; i < total; i++)
			files[i] = FileUtil.createFile(i + ".mock2", proj);

		for (int i = 0; i < total; i++) {
			editors[i] = IDE.openEditor(fActivePage, files[i], true);
			callTraces[i] = ((MockEditorPart) editors[i]).getCallHistory();
		}
		assertEquals(fActivePage.closeAllEditors(true), true);
		for (int i = 0; i < total; i++) {
			assertEquals(callTraces[i].contains("isDirty"), true);
			assertEquals(callTraces[i].contains("doSave"), false);
			callTraces[i].clear();
		}


		for (int i = 0; i < total; i++) {
			editors[i] = IDE.openEditor(fActivePage, files[i], true);
			mocks[i] = (MockEditorPart) editors[i];
			mocks[i].setDirty(true);
			callTraces[i] = mocks[i].getCallHistory();
		}
		assertEquals(fActivePage.closeAllEditors(false), true);
		for (int i = 0; i < total; i++) {
			assertEquals(callTraces[i].contains("doSave"), false);
		}
	}

	public void testSaveEditor() throws Throwable {
		proj = FileUtil.createProject("testOpenEditor");
		final IFile file = FileUtil.createFile("test.mock1", proj);
		IEditorPart editor;
		CallHistory callTrace;
		MockEditorPart mock;

		editor = IDE.openEditor(fActivePage, file, true);
		mock = (MockEditorPart) editor;
		callTrace = mock.getCallHistory();
		callTrace.clear();

		assertEquals(fActivePage.saveEditor(editor, true), true);
		assertEquals(callTrace.contains("isDirty"), true);
		assertEquals(callTrace.contains("doSave"), false);

		assertEquals(fActivePage.saveEditor(editor, false), true);
		assertEquals(callTrace.contains("isDirty"), true);
		assertEquals(callTrace.contains("doSave"), false);

		mock.setDirty(true);
		callTrace.clear();
		assertEquals(fActivePage.saveEditor(editor, false), true);
		assertEquals(callTrace
				.verifyOrder(new String[] { "isDirty", "doSave" }), true);
	}

	public void XXXtestSaveAllEditors() throws Throwable {
		int total = 3;

		final IFile[] files = new IFile[total];
		IEditorPart[] editors = new IEditorPart[total];
		CallHistory[] callTraces = new CallHistory[total];
		MockEditorPart[] mocks = new MockEditorPart[total];

		proj = FileUtil.createProject("testOpenEditor");
		for (int i = 0; i < total; i++) {
			files[i] = FileUtil.createFile(i + ".mock2", proj);
			editors[i] = IDE.openEditor(fActivePage, files[i], true);
			mocks[i] = (MockEditorPart) editors[i];
			callTraces[i] = mocks[i].getCallHistory();
		}

		assertEquals(fActivePage.saveAllEditors(true), true);
		for (int i = 0; i < total; i++) {
			assertEquals(callTraces[i].contains("isDirty"), true);
			assertEquals(callTraces[i].contains("doSave"), false);
			callTraces[i].clear();
		}


		assertEquals(fActivePage.saveAllEditors(false), true);
		for (int i = 0; i < total; i++) {
			assertEquals(callTraces[i].contains("isDirty"), true);
			assertEquals(callTraces[i].contains("doSave"), false);
			callTraces[i].clear();
		}

		for (int i = 0; i < total; i++)
			mocks[i].setDirty(true);
		assertEquals(fActivePage.saveAllEditors(false), true);
		for (int i = 0; i < total; i++)
			assertEquals(callTraces[i].verifyOrder(new String[] { "isDirty",
					"doSave" }), true);
	}

	public void testGetEditors() throws Throwable {
		proj = FileUtil.createProject("testOpenEditor");
		int totalBefore = fActivePage.getEditors().length;
		int num = 3;
		IEditorPart[] editors = new IEditorPart[num];

		for (int i = 0; i < num; i++) {
			editors[i] = IDE.openEditor(fActivePage, FileUtil.createFile(i
					+ ".mock2", proj), true);
			assertEquals(ArrayUtil.contains(fActivePage.getEditors(),
					editors[i]), true);
		}
		assertEquals(fActivePage.getEditors().length, totalBefore + num);

		fActivePage.closeEditor(editors[0], false);
		assertEquals(ArrayUtil.contains(fActivePage.getEditors(), editors[0]),
				false);
		assertEquals(fActivePage.getEditors().length, totalBefore + num - 1);

		fActivePage.closeAllEditors(false);
		assertEquals(fActivePage.getEditors().length, 0);
	}

	public void XXXtestShowActionSet() {
		String id = MockActionDelegate.ACTION_SET_ID;

		fail("facade.getActionSetCount() had no implementation");

		fActivePage.showActionSet(id);


		id = IConstants.FakeID;
		fActivePage.showActionSet(id);

		fail("facade.assertActionSetId() had no implementation");

	}

	public void XXXtestHideActionSet() {

		fail("facade.getActionSetCount() had no implementation");

		String id = MockWorkbenchWindowActionDelegate.SET_ID;
		fActivePage.showActionSet(id);

		fActivePage.hideActionSet(id);

		
		fail("facade.assertActionSetId() had no implementation");
	}
}
