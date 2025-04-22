    }
    
    public boolean closeEditor(IEditorReference editorRef, boolean save) {
        return closeEditors(new IEditorReference[] {editorRef}, save);
    }

    /**
     * See IWorkbenchPage#closeEditor
     */
    public boolean closeEditor(IEditorPart editor, boolean save) {
        IWorkbenchPartReference ref = getReference(editor);
        if (ref instanceof IEditorReference) {
        	return closeEditors(new IEditorReference[] {(IEditorReference) ref}, save);
        }
        return false;
    }
