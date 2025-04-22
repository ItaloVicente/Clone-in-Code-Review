
	@Test
	public void openMultipleEditorsAndCloseThemAfterwards() {
		int numberOfFiles = 99;
		String fileName = "tempFile";
		String fileExtension = ".txt";
		IWorkbenchPage page = workbenchWindow.getActivePage();
		List<IEditorPart> editors = new ArrayList<>();
		try {
			FileUtil.createProject("TestProject");
			IProject testProject = ResourcesPlugin.getWorkspace().getRoot().getProject("TestProject"); //$NON-NLS-1$
			for (int index = 0; index < numberOfFiles; index++) {
				String file = fileName + index + fileExtension;
				FileUtil.createFile(file, testProject);
				IEditorInput editorInput = new FileEditorInput(testProject.getFile(file));
				editors.add(page.openEditor(editorInput, "org.eclipse.ui.DefaultTextEditor")); //$NON-NLS-1$
			}
			editors.stream().forEach(e -> page.closeEditor(e, false));
			FileUtil.deleteProject(testProject);
		} catch (Exception e) {
			fail("Opening and closing multiple editors should not result in an exception");
		}
		IEditorPart part = page.getActiveEditor();
		boolean anyMatch = editors.stream().anyMatch(e -> e.equals(part));
		assertFalse("Active Editor should not be one of the closed ones", anyMatch);
	}
