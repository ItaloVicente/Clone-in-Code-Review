
	File getActiveFile() {
		IWorkbenchPage workbenchPage = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getService(IWorkbenchPage.class);
		IEditorPart activeEditor = workbenchPage.getActiveEditor();
		if (activeEditor == null) {
			return null;
		}

		IEditorInput editorInput = activeEditor.getEditorInput();
		return Adapters.adapt(editorInput, File.class);
	}
