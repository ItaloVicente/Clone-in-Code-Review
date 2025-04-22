	/**
	 * Create and hide a single editor.  Close it while it's hidden
	 * and make sure that it doesn't die.
	 *
	 * @throws Exception
	 */
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
