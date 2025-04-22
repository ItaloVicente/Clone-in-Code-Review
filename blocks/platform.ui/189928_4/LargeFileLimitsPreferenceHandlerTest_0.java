	@Test
	public void testOpenEditorWithIgnoreSize() throws Exception {
		String testEditorId = TEST_EDITOR_ID2;
		long fileSize = 4L;
		List<FileLimit> fileLimits = Arrays.asList(new FileLimit(testEditorId, fileSize));
		configureFileLimits(fileLimits);

		Class<?> expectedEditorClass = org.eclipse.ui.tests.api.MockEditorPart.class;
		IEditorInput editorInput = testEditorInput;
		String editorIdForOpen = "org.eclipse.ui.tests.api.MockEditorPart1";

		IWorkbenchPage page = getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorPart editor = null;
		try {
			boolean activate = true;
			int flags = IWorkbenchPage.MATCH_NONE | IWorkbenchPage.MATCH_IGNORE_SIZE;
			editor = page.openEditor(editorInput, editorIdForOpen, activate, flags);
			assertEquals("Wrong editor opened", expectedEditorClass, editor.getClass());
		} finally {
			if (editor != null) {
				editor.dispose();
			}
		}
	}

