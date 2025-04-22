		IWorkbenchWindow w = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (w == null)
			return;
		IWorkbenchPage page = w.getActivePage();
		if (page == null)
			return;
		IFileEditorInput input = new FileEditorInput((IFile) marker.getResource());
		IEditorPart editorPart = page.findEditor(input);
