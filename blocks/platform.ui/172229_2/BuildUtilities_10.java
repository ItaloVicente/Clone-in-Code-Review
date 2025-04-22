		} else //see if we can extract a selected project from the active editor
		if (activePart instanceof IEditorPart) {
			IEditorPart editor= (IEditorPart)activePart;
			IFile file = ResourceUtil.getFile(editor.getEditorInput());
			if (file != null) {
				selected = new IProject[] {file.getProject()};
