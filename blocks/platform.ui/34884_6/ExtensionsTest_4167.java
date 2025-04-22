package org.eclipse.ui.tests.navigator;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TreeItem;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.tests.harness.util.DisplayHelper;
import org.eclipse.ui.tests.harness.util.SWTEventHelper;
import org.eclipse.ui.tests.navigator.extension.TestDragAssistant;

public class DnDTest extends NavigatorTestBase {

	public DnDTest() {
		_navigatorInstanceId = TEST_VIEWER;
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testBasicDragDrop() throws Exception {
		_viewer.expandToLevel(_p1, 3);

		_viewer.setSelection(new StructuredSelection(_p1.getFolder("f1")
				.getFile("file1.txt")));

		TreeItem[] items = _viewer.getTree().getItems();

		TreeItem start = items[_p1Ind].getItem(0).getItem(0);
		TreeItem end = items[_p1Ind].getItem(1);
		if (!SWTEventHelper.performDnD(start, end)) {
			System.out.println("Drag and drop failed - test invalid");
			return;
		}

		_viewer.expandToLevel(_p1, 3);
		items = _viewer.getTree().getItems();

		assertEquals(_p1.getFolder("f1").getFile("file2.txt"), items[_p1Ind]
				.getItem(0).getItem(0).getData());
		assertEquals(_p1.getFolder("f2").getFile("file1.txt"), items[_p1Ind]
				.getItem(1).getItem(0).getData());

		assertFalse(_p1.getFolder("f1").getFile("file1.txt").exists());
		assertTrue(_p1.getFolder("f2").getFile("file1.txt").exists());

	}

	public void testResourceDrag() throws Exception {
		_viewer.expandToLevel(_p1, 3);

		IFile file = _p1.getFolder("f1").getFile("file1.txt");

		_viewer.setSelection(new StructuredSelection(file));

		IWorkbenchPage activePage = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		TextEditor editorPart = (TextEditor) IDE.openEditor(activePage, file);

		Control end = (Control) editorPart.getAdapter(Control.class);
		
		TreeItem[] items = _viewer.getTree().getItems();

		TreeItem start = items[_p1Ind].getItem(0).getItem(0);

		if (!SWTEventHelper.performDnD(start, end)) {
			System.out.println("Drag and drop failed - test invalid");
			return;
		}

		assertNotNull(TestDragAssistant._finishedEvent);
		assertNotNull(TestDragAssistant._finishedSelection);
	}

	public void testDragOptOut() throws Exception {
		_viewer.expandToLevel(_p1, 3);

		IFile file = _p1.getFolder("f1").getFile("file1.txt");

		_viewer.setSelection(new StructuredSelection(file));

		IWorkbenchPage activePage = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		TextEditor editorPart = (TextEditor) IDE.openEditor(activePage, file);

		Control end = (Control) editorPart.getAdapter(Control.class);
		
		TreeItem[] items = _viewer.getTree().getItems();

		TreeItem start = items[_p1Ind].getItem(0).getItem(0);

		TestDragAssistant._doit = false;
		
		if (!SWTEventHelper.performDnD(start, end)) {
			System.out.println("Drag and drop failed - test invalid");
			return;
		}

		assertFalse(TestDragAssistant._dragSetDataCalled);
	}

	public void testSetDragOperation() throws Exception {

		_contentService.bindExtensions(new String[] { TEST_CONTENT_DROP_COPY },
				false);
		_contentService.getActivationService().activateExtensions(
				new String[] { TEST_CONTENT_DROP_COPY }, false);

		_viewer.expandToLevel(_p1, 3);

		_viewer.setSelection(new StructuredSelection(_p1.getFolder("f1")
				.getFile("file1.txt")));

		DisplayHelper.sleep(100);

		TreeItem[] items = _viewer.getTree().getItems();

		int firstFolder = 0;

		TreeItem start = items[_p1Ind].getItem(firstFolder).getItem(0);

		TreeItem end = items[_p1Ind].getItem(firstFolder + 1);
		if (!SWTEventHelper.performDnD(start, end)) {
			System.out.println("Drag and drop failed - test invalid");
			return;
		}

		refreshViewer();
		DisplayHelper.sleep(100);
		_viewer.expandToLevel(_p1, 3);
		items = _viewer.getTree().getItems();
		
		assertEquals(_p1.getFolder("f1").getFile("file1.txt"), items[_p1Ind]
				.getItem(firstFolder).getItem(0).getData());
		assertEquals(_p1.getFolder("f1").getFile("file2.txt"), items[_p1Ind]
				.getItem(firstFolder).getItem(1).getData());
		
		assertEquals(_p1.getFolder("f2").getFile("file1.txt"), items[_p1Ind]
				.getItem(firstFolder + 1).getItem(0).getData());

		assertTrue(_p1.getFolder("f1").getFile("file1.txt").exists());
		assertTrue(_p1.getFolder("f2").getFile("file1.txt").exists());
	}

}
