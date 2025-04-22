        navigationHistory.markEditor(editor);
    }

    /**
     * See IWorkbenchPage.
     */
    public IEditorPart openEditor(IEditorInput input, String editorID)
            throws PartInitException {
        return openEditor(input, editorID, true, MATCH_INPUT);
    }

    /**
     * See IWorkbenchPage.
     */
    public IEditorPart openEditor(IEditorInput input, String editorID,
			boolean activate) throws PartInitException {
