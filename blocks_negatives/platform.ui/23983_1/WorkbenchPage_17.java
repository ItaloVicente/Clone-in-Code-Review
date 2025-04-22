    }
	
    /**
     * See IWorkbenchPage.
     */
    public IEditorPart openEditor(final IEditorInput input,
            final String editorID, final boolean activate, final int matchFlags)
            throws PartInitException {
    	return openEditor(input, editorID, activate, matchFlags, null, true);
    }
