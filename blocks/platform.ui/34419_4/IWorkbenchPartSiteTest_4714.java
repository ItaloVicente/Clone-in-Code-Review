		fActivePage.closeEditors(new IEditorReference[] { editorRef2 }, true);
		refs = fActivePage.getEditorReferences();
		assertEquals(1, refs.length);
		fActivePage.showEditor(editorRef2);
		assertEquals(1, refs.length);
		assertEquals(getMessage(), 1, logCount);
		assertNotNull(getMessage());
		assertTrue(getMessage().startsWith("adding a disposed part"));
	}

	public void XXXtestOpenAndHideEditor4() throws Exception {
		proj = FileUtil.createProject("testOpenAndHideEditor");
		IFile file1 = FileUtil.createFile("a.mock1", proj);
		IFile file2 = FileUtil.createFile("a.mock2", proj);
		IEditorPart editor = IDE.openEditor(fActivePage, file1);
		assertTrue(editor instanceof MockEditorPart);
		IEditorReference editorRef = (IEditorReference) fActivePage
				.getReference(editor);
		IEditorPart editor2 = IDE.openEditor(fActivePage, file2);
		assertTrue(editor2 instanceof MockEditorPart);
		IEditorReference editorRef2 = (IEditorReference) fActivePage.getReference(editor2);

		fActivePage.hideEditor(editorRef2);
		IEditorReference[] refs = fActivePage.getEditorReferences();
		assertEquals(1, refs.length);
		assertEquals(editorRef, refs[0]);
		fActivePage.showEditor(editorRef2);
		refs = fActivePage.getEditorReferences();
		assertEquals(2, refs.length);
		fActivePage.showEditor(editorRef2);
		refs = fActivePage.getEditorReferences();
		assertEquals(2, refs.length);
		assertEquals(getMessage(), 0, logCount);
	}

	public void XXXtestOpenAndHideEditor5() throws Exception {
		proj = FileUtil.createProject("testOpenAndHideEditor");
		IFile file1 = FileUtil.createFile("a1.java", proj);
		IFile file2 = FileUtil.createFile("a2.java", proj);
		IEditorPart editor = IDE.openEditor(fActivePage, file1);
		assertTrue(editor.getClass().getName().endsWith("CompilationUnitEditor"));
		IEditorReference editorRef = (IEditorReference) fActivePage
				.getReference(editor);
		IEditorPart editor2 = IDE.openEditor(fActivePage, file2);
		assertTrue(editor2.getClass().getName().endsWith("CompilationUnitEditor"));

		ContentOutline outline = (ContentOutline) fActivePage.showView(IPageLayout.ID_OUTLINE);
		IPage page2 = outline.getCurrentPage();
		fActivePage.activate(editor);
		processEvents();
		IPage page = outline.getCurrentPage();
		assertFalse(page2==page);

		assertEquals(getMessage(), 0, logCount);

		fActivePage.hideEditor(editorRef);
		assertEquals(page2, outline.getCurrentPage());
		assertEquals(getMessage(), 0, logCount);

		fActivePage.showEditor(editorRef);
		assertEquals(page2, outline.getCurrentPage());
		assertEquals(getMessage(), 0, logCount);

		fActivePage.activate(editor);
		assertEquals(page, outline.getCurrentPage());
		assertEquals(getMessage(), 0, logCount);
	}

	public void XXXtestOpenAndHideEditor6() throws Exception {
		proj = FileUtil.createProject("testOpenAndHideEditor");
		IFile file1 = FileUtil.createFile("a1.java", proj);
		IEditorPart editor = IDE.openEditor(fActivePage, file1);
		assertTrue(editor.getClass().getName().endsWith("CompilationUnitEditor"));
		IEditorReference editorRef = (IEditorReference) fActivePage
				.getReference(editor);

		ContentOutline outline = (ContentOutline) fActivePage.showView(IPageLayout.ID_OUTLINE);
		IPage defaultPage = outline.getDefaultPage();
		assertNotNull(defaultPage);

		processEvents();
		IPage page = outline.getCurrentPage();
		assertFalse(defaultPage==page);

		assertEquals(getMessage(), 0, logCount);
		assertEquals(0, partHiddenCount);
		fActivePage.addPartListener(partListener2);
		fActivePage.hideEditor(editorRef);
		processEvents();

		assertEquals(1, partHiddenCount);
		assertEquals(editorRef, partHiddenRef);

		assertEquals(defaultPage, outline.getCurrentPage());
		assertEquals(getMessage(), 0, logCount);

		assertEquals(0, partVisibleCount);
		fActivePage.showEditor(editorRef);
		processEvents();
		assertEquals(page, outline.getCurrentPage());
		assertEquals(getMessage(), 0, logCount);
		assertEquals(1, partVisibleCount);
		assertEquals(editorRef, partVisibleRef);

		fActivePage.activate(editor);
		assertEquals(page, outline.getCurrentPage());
		assertEquals(getMessage(), 0, logCount);
	}

	public void XXXtestOpenAndHideEditor7() throws Exception {
		proj = FileUtil.createProject("testOpenAndHideEditor");
		IFile file1 = FileUtil.createFile("a1.java", proj);
		IEditorPart editor = IDE.openEditor(fActivePage, file1);
		assertTrue(editor.getClass().getName().endsWith("CompilationUnitEditor"));
		IEditorReference editorRef = (IEditorReference) fActivePage
				.getReference(editor);

		processEvents();

		String firstTitle = fWin.getShell().getText();

		assertEquals(getMessage(), 0, logCount);
		assertEquals(0, partHiddenCount);
		fActivePage.addPartListener(partListener2);
		fActivePage.hideEditor(editorRef);
		processEvents();

		assertEquals(1, partHiddenCount);
		assertEquals(editorRef, partHiddenRef);

		String nextTitle = fWin.getShell().getText();
		String tooltip = editor.getTitleToolTip();
		assertNotNull(tooltip);
		String[] split = Util.split(nextTitle, '-');
		assertEquals(2, split.length);
		String nextTitleRebuilt = split[0] + "- " + tooltip + " -" + split[1];
		assertEquals(firstTitle, nextTitleRebuilt);

		assertEquals(0, partVisibleCount);
		fActivePage.showEditor(editorRef);
		processEvents();
		assertEquals(getMessage(), 0, logCount);
		assertEquals(1, partVisibleCount);
		assertEquals(editorRef, partVisibleRef);
		nextTitle = fWin.getShell().getText();
		assertEquals(firstTitle, nextTitle);

		fActivePage.activate(editor);
		assertEquals(getMessage(), 0, logCount);
	}

	public void XXXtestOpenAndHideEditor8() throws Exception {
		proj = FileUtil.createProject("testOpenAndHideEditor");
		IFile file1 = FileUtil.createFile("a1.java", proj);
		IEditorPart editor = IDE.openEditor(fActivePage, file1);
		assertTrue(editor.getClass().getName().endsWith("CompilationUnitEditor"));
		IEditorReference editorRef = (IEditorReference) fActivePage
				.getReference(editor);

		ContentOutline outline = (ContentOutline) fActivePage.showView(IPageLayout.ID_OUTLINE);
		IPage defaultPage = outline.getDefaultPage();
		assertNotNull(defaultPage);
		fActivePage.activate(editor);

		processEvents();
		IPage page = outline.getCurrentPage();
		assertFalse(defaultPage==page);

		partActiveCount = 0;
		partActiveRef = null;
		assertEquals(getMessage(), 0, logCount);
		assertEquals(0, partHiddenCount);
		fActivePage.addPartListener(partListener2);
		fActivePage.hideEditor(editorRef);
		processEvents();

		assertEquals(1, partHiddenCount);
		assertEquals(editorRef, partHiddenRef);
		assertEquals(1, partActiveCount);
		assertFalse(partActiveRef == editorRef);

		fActivePage.showEditor(editorRef);

		assertEquals(getMessage(), 0, logCount);
	}

	public void XXXtestOpenAndHideEditor9() throws Exception {
		proj = FileUtil.createProject("testOpenAndHideEditor");
		IFile file1 = FileUtil.createFile("a1.java", proj);
		IEditorPart editor = IDE.openEditor(fActivePage, file1);
		assertTrue(editor.getClass().getName()
				.endsWith("CompilationUnitEditor"));
		IEditorReference editorRef = (IEditorReference) fActivePage
				.getReference(editor);

		fActivePage.activate(editor);

		processEvents();
		ICommandService cs = fActivePage.getWorkbenchWindow()
				.getService(ICommandService.class);
		Command undo = cs.getCommand("org.eclipse.ui.edit.undo");
		assertTrue(undo.isDefined());

		assertFalse(undo.isEnabled());

		ITextEditor textEditor = (ITextEditor) editor;
		IDocument doc = textEditor.getDocumentProvider().getDocument(
				textEditor.getEditorInput());
		doc.replace(0, 1, "  ");
		fActivePage.saveEditor(editor, false);

		processEvents();
		assertTrue(undo.isEnabled());

		assertEquals(getMessage(), 0, logCount);
		fActivePage.hideEditor(editorRef);
		processEvents();

		assertFalse(undo.isEnabled());

		fActivePage.showEditor(editorRef);

		assertTrue(undo.isEnabled());

		assertEquals(getMessage(), 0, logCount);
	}

	public void XXXtestOpenAndHideEditor10() throws Exception {
		proj = FileUtil.createProject("testOpenAndHideEditor");
		IFile file1 = FileUtil.createFile("a.mock1", proj);
		IEditorPart editor = IDE.openEditor(fActivePage, file1);
		assertTrue(editor instanceof MockEditorPart);
		IEditorReference editorRef = (IEditorReference) fActivePage
				.getReference(editor);
		fActivePage.hideEditor(editorRef);
		assertEquals(0, fActivePage.getEditorReferences().length);
		fActivePage.showEditor(editorRef);
		assertEquals(1, fActivePage.getEditorReferences().length);
		fActivePage.hideEditor(editorRef);
		processEvents();
		fActivePage.closeAllEditors(false);
		assertEquals(getMessage(), 0, logCount);
		assertEquals(0, fActivePage.getEditorReferences().length);
		((WorkbenchPage)fActivePage).resetHiddenEditors();
		assertEquals(0, fActivePage.getEditorReferences().length);
	}

	public void testOpenEditors1() throws Throwable {
		proj = FileUtil.createProject("testOpenEditors");
		IFile[] inputs = new IFile[1];
		String fileName0 = "test0.txt";
		inputs[0] = FileUtil.createFile(fileName0, proj);

		IEditorReference[] refs = IDE.openEditors(fActivePage, inputs);
		assertNotNull(refs);
		assertEquals(1, refs.length);
		assertNotNull(refs[0]);

		IEditorPart editor0 = refs[0].getEditor(false);
		assertNotNull(editor0);

		assertEquals(fActivePage.getActiveEditor(), editor0);

		assertEquals(editor0.getSite().getId(), fWorkbench.getEditorRegistry()
				.getDefaultEditor(inputs[0].getName()).getId());

		assertEquals(fileName0, refs[0].getTitle());
	}

	public void testOpenEditors3() throws Throwable {
		proj = FileUtil.createProject("testOpenEditors");
		IFile[] inputs = new IFile[3];
		String fileName1 = "test1.txt";
		String fileName2 = "test2.txt";
		String fileName3 = "test3.txt";
		inputs[0] = FileUtil.createFile(fileName1, proj);
		inputs[1] = FileUtil.createFile(fileName2, proj);
		inputs[2] = FileUtil.createFile(fileName3, proj);

		IEditorReference[] refs = IDE.openEditors(fActivePage, inputs);
		assertNotNull(refs);
		assertEquals(3, refs.length);
		assertNotNull(refs[0]);
		assertNotNull(refs[1]);
		assertNotNull(refs[2]);

		IEditorPart editor0 = refs[0].getEditor(false);
		assertNotNull(editor0);
		assertNull(refs[1].getEditor(false));
		assertNull(refs[2].getEditor(false));

		assertEquals(fActivePage.getActiveEditor(), editor0);

		assertEquals(editor0.getSite().getId(), fWorkbench.getEditorRegistry()
				.getDefaultEditor(inputs[0].getName()).getId());

		IEditorPart editor1 = refs[1].getEditor(true);
		assertNotNull(editor1);

		assertEquals(editor1.getSite().getId(), fWorkbench.getEditorRegistry()
				.getDefaultEditor(inputs[1].getName()).getId());

		assertEquals(fileName1, refs[0].getTitle());
		assertEquals(fileName2, refs[1].getTitle());
		assertEquals(fileName3, refs[2].getTitle());
	}

	public void XXXtestOpenEditorsReuse() throws Throwable {
		proj = FileUtil.createProject("testOpenEditors");

		String fileName1 = "test1.txt";
		String fileName2 = "test2.txt";
		String fileName3 = "test3.txt";
		int flag = IWorkbenchPage.MATCH_INPUT | IWorkbenchPage.MATCH_ID; // use both matches

		IFile[] inputs = new IFile[3];
		inputs[0] = FileUtil.createFile(fileName1, proj);
		inputs[1] = FileUtil.createFile(fileName2, proj);
		inputs[2] = FileUtil.createFile(fileName3, proj);
		IEditorReference[] refs = IDE.openEditors(fActivePage, inputs);

		String editorID = fWorkbench.getEditorRegistry().getDefaultEditor(inputs[0].getName()).getId();
		IEditorInput[] inputs2 = new IEditorInput[] {
				new FileEditorInput(inputs[1]),
				new FileEditorInput(inputs[0]) };
		String[] editorIDs2 = new String [] { editorID, editorID} ;

		IEditorReference[] refs2 = fActivePage.openEditors(inputs2, editorIDs2, flag);
		assertNotNull(refs2);
		assertEquals(2, refs2.length);

		IEditorPart editor = refs2[0].getEditor(false);
		assertNotNull(editor);
		assertEquals(fActivePage.getActiveEditor(), editor);

		assertEquals(refs2[0].getEditor(true), refs[1].getEditor(true));
		assertEquals(refs2[1].getEditor(true), refs[0].getEditor(true));

		String editorIDAlt = fWorkbench.getEditorRegistry().getDefaultEditor("abc.log").getId();
		IEditorInput[] inputs3 = new IEditorInput[] {
				new FileEditorInput(inputs[0]),
				new FileEditorInput(inputs[2]) };
		String[] editorIDs3 = new String [] { editorIDAlt, editorIDAlt} ;

		IEditorReference[] refs3 = fActivePage.openEditors(inputs3, editorIDs3, flag);
		assertNotNull(refs3);
		assertEquals(2, refs3.length);

		assertFalse(refs2[0].equals(refs[0]));
		assertFalse(refs2[1].equals(refs[2]));
	}

	public void testSetPartState() throws Exception {
		IViewPart view = fActivePage.showView(MockViewPart.ID);

		IViewReference reference = (IViewReference) fActivePage
				.getReference(view);
		fActivePage.setPartState(reference, IWorkbenchPage.STATE_MINIMIZED);

		assertTrue("A minimized view should be a fast view", APITestUtils.isFastView(reference));

		fActivePage.setPartState(reference, IWorkbenchPage.STATE_RESTORED);
		assertFalse("A restored view should not be a fast view", APITestUtils.isFastView(reference));
	}

	public void XXXtestOpenAndHideEditor11() throws Exception {
		proj = FileUtil.createProject("testOpenAndHideEditor");
		IFile file1 = FileUtil.createFile("a.mock1", proj);

		IWorkbenchWindow window = openTestWindow();
		IWorkbenchPage page = window.getActivePage();

		IEditorPart editor = IDE.openEditor(page, file1);
		assertTrue(editor instanceof MockEditorPart);
		IEditorReference editorRef = (IEditorReference) page
				.getReference(editor);
		page.hideEditor(editorRef);
		assertEquals(0, page.getEditorReferences().length);
		page.showEditor(editorRef);
		assertEquals(1, page.getEditorReferences().length);
		page.hideEditor(editorRef);
		processEvents();
		window.close();
		processEvents();
		assertEquals(getMessage(), 0, logCount);
		assertEquals(0, page.getEditorReferences().length);
	}

	public void XXXtestOpenAndHideEditor12() throws Exception {
		proj = FileUtil.createProject("testOpenAndHideEditor");
		IFile file1 = FileUtil.createFile("a.mock1", proj);
		IEditorPart editor = IDE.openEditor(fActivePage, file1);
		assertTrue(editor instanceof MockEditorPart);
		IEditorReference editorRef = (IEditorReference) fActivePage
				.getReference(editor);
		fActivePage.hideEditor(editorRef);
		assertEquals(0, fActivePage.getEditorReferences().length);
		fActivePage.showEditor(editorRef);
		assertEquals(1, fActivePage.getEditorReferences().length);
		fActivePage.hideEditor(editorRef);
		processEvents();
		fActivePage.closeEditor(editor, false);
		processEvents();
		((WorkbenchPage)fActivePage).resetHiddenEditors();
		assertEquals(0, fActivePage.getEditorReferences().length);
		assertEquals(getMessage(), 0, logCount);
		assertEquals(0, fActivePage.getEditorReferences().length);
	}
}
