			FileUtil.createProject("TestProject");
			IProject testProject = ResourcesPlugin.getWorkspace().getRoot().getProject("TestProject"); //$NON-NLS-1$
			FileUtil.createFile("tempFile.txt", testProject);
			testProject.open(null);
			IEditorInput editorInput = new FileEditorInput(testProject.getFile("tempFile.txt"));
			IEditorPart editorPart = null;
			for (index = 0; index < numIterations; index++) {
				editorPart = page.openEditor(editorInput, "org.eclipse.ui.DefaultTextEditor"); //$NON-NLS-1$
				page.closeEditor(editorPart, false);
			}
