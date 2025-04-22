	@Test
	public void testDisabledDefaultLimit() throws Exception {
		String testEditorId = "org.eclipse.ui.tests.api.MockEditorPart1";
		long fileSize = 1L;
		LargeFileLimitsPreferenceHandler.setDefaultLimit(fileSize);
		LargeFileLimitsPreferenceHandler.disableDefaultLimit();

		IEditorRegistry editorRegistry = getWorkbench().getEditorRegistry();
		IEditorDescriptor testEditor = editorRegistry.findEditor(testEditorId);
		assertNotNull("Expected to find editor with ID: " + testEditorId, testEditor);

		testPromptForEditor.selectedEditor = testEditor;
		testPromptForEditor.rememberSelection = false;
		assertNoEditorIsChosen();
	}

