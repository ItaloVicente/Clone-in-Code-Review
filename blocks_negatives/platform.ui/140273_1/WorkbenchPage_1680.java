        navigationHistory.markEditor(editor);
    }

    /**
     * See IWorkbenchPage.
     */
    @Override
	public IEditorPart openEditor(IEditorInput input, String editorID)
            throws PartInitException {
        return openEditor(input, editorID, true, MATCH_INPUT);
    }

    /**
     * See IWorkbenchPage.
     */
    @Override
	public IEditorPart openEditor(IEditorInput input, String editorID,
			boolean activate) throws PartInitException {
