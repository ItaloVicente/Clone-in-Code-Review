		for (IWorkbenchWindow window : windows) {
			IWorkbenchPage[] pages = window.getPages();
			for (IWorkbenchPage page : pages) {
				IEditorReference[] editorReferences = page.getEditorReferences();
				for (IEditorReference editorReference : editorReferences)
					closeEditor(editorReference.getEditor(false));
