	public void testTextEditorContextMenu() throws Exception {
		proj = FileUtil.createProject("testEditorLeaks");

		IEditorInput input = new NullEditorInput();
		ReferenceQueue queue = new ReferenceQueue();
		IEditorPart editor = IDE.openEditor(fActivePage, input, "org.eclipse.ui.tests.leak.contextEditor");
		assertTrue(editor instanceof ContextEditorPart);
		Reference ref = createReference(queue, editor);

		ContextEditorPart contextMenuEditor = (ContextEditorPart) editor;

		contextMenuEditor.showMenu();
		processEvents();

		contextMenuEditor.hideMenu();
		processEvents();

		try {
			contextMenuEditor = null;
			fActivePage.closeEditor(editor, false);
			editor = null;
			checkRef(queue, ref);
		} finally {
			ref.clear();
		}
	}

	public void testSimpleWindowLeak() throws Exception {
		manageWindows(false);
		try {
			ReferenceQueue queue = new ReferenceQueue();
			IWorkbenchWindow newWindow = openTestWindow();

			assertNotNull(newWindow);
			Reference ref = createReference(queue, newWindow);
			try {
				newWindow.close();
				newWindow = null;
				checkRef(queue, ref);
			} finally {
				ref.clear();
			}
		} finally {
			manageWindows(true);
		}
	}

	public void testDestroyedDialogLeaks() throws Exception {
		ReferenceQueue queue = new ReferenceQueue();
		Dialog newDialog = new SaveAsDialog(fWin.getShell());
		newDialog.setBlockOnOpen(false);
		newDialog.open();
		assertNotNull(newDialog);
		Reference ref = createReference(queue, newDialog);
		try {
			newDialog.getShell().dispose();
			newDialog.close();
			newDialog = null;
			checkRef(queue, ref);
		} finally {
			ref.clear();
		}
	}
