		navigationHistory.markEditor(editor);
	}

	public IEditorPart openEditor(IEditorInput input, String editorID) throws PartInitException {
		return openEditor(input, editorID, true, MATCH_INPUT);
	}

	public IEditorPart openEditor(IEditorInput input, String editorID, boolean activate)
			throws PartInitException {
