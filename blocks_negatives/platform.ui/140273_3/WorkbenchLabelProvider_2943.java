    /**
     * Creates a new workbench label provider.
     */
    public WorkbenchLabelProvider() {
    	PlatformUI.getWorkbench().getEditorRegistry().addPropertyListener(editorRegistryListener);
    }
