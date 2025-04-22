	@Test
	public void testDisabledDefaultLimit() throws Exception {
		long fileSize = 1L;
		LargeFileLimitsPreferenceHandler.setDefaultLimit(fileSize);
		LargeFileLimitsPreferenceHandler.disableDefaultLimit();

		Class<?> expectedEditorClass = org.eclipse.ui.editors.text.TextEditor.class;
		IEditorInput editorInput = testEditorInput;
		String editorIdForOpen = "org.eclipse.ui.DefaultTextEditor";

		IWorkbenchPage page = getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorPart editor = null;
		try {
			editor = page.openEditor(editorInput, editorIdForOpen);
			assertEquals("Wrong editor opened", expectedEditorClass, editor.getClass());
		} finally {
			if (editor != null) {
				editor.dispose();
			}
		}
	}

