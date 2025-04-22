		} else {
			if (activePart instanceof IEditorPart) {
				IEditorPart editor= (IEditorPart)activePart;
				IFile file = ResourceUtil.getFile(editor.getEditorInput());
				if (file != null) {
					selected = new IProject[] {file.getProject()};
				}
