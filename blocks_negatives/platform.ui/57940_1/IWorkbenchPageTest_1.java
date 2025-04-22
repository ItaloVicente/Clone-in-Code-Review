	/**
	 * Create and hide a single editor, and check it is reflected in the
	 * editor references.  Check that closing the hidden editor still works.
	 *
	 * @throws Exception
	 */
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

