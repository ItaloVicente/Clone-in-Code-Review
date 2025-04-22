    /**
     * See IWorkbenchPage.
     */
    public boolean isEditorPinned(IEditorPart editor) {
    	WorkbenchPartReference ref = (WorkbenchPartReference)getReference(editor); 
        return ref != null && ref.isPinned();
    }
    

