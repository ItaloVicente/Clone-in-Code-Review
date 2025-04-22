/*******************************************************************************
 * Copyright (c) 2009, 2015 Oakland Software Incorporated and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     Francis Upton IV, Oakland Software - Initial implementation
 *     Thibault Le Ouay <thibaultleouay@gmail.com> - Bug 457870
 *******************************************************************************/
package org.eclipse.ui.tests.navigator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.tests.harness.util.DisplayHelper;
import org.eclipse.ui.tests.harness.util.SWTEventHelper;
import org.eclipse.ui.tests.navigator.extension.TestDragAssistant;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DnDTest extends NavigatorTestBase {

	public DnDTest() {
		_navigatorInstanceId = TEST_VIEWER;
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Override
	@After
	public void tearDown() {
		super.tearDown();
	}

	@Test
	public void testBasicDragDrop() {
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

	@Test
	public void testResourceDrag() {
		_viewer.expandToLevel(_p1, 3);

		IFile file = _p1.getFolder("f1").getFile("file1.txt");

		_viewer.setSelection(new StructuredSelection(file));

		IWorkbenchPage activePage = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		TextEditor editorPart = null;
		try {
			editorPart = (TextEditor) IDE.openEditor(activePage, file);
		} catch (PartInitException e) {
			fail("Should not throw an exception");
		}

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

	@Test
	public void testDragOptOut() {
		_viewer.expandToLevel(_p1, 3);

		IFile file = _p1.getFolder("f1").getFile("file1.txt");

		_viewer.setSelection(new StructuredSelection(file));

		IWorkbenchPage activePage = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		TextEditor editorPart = null;
		try {
			editorPart = (TextEditor) IDE.openEditor(activePage, file);
		} catch (PartInitException e) {
			fail("Should not throw an exception");
		}

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

	@Test
	public void testSetDragOperation() {

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
