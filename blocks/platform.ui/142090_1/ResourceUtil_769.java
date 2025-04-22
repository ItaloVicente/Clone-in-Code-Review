	}

	public static IEditorPart findEditor(IWorkbenchPage page, IFile file) {
		IEditorPart editor = page.findEditor(new FileEditorInput(file));
		if (editor != null) {
			return editor;
		}
