    /**
     * See IWorkbenchPage
     */
    public boolean closeAllEditors(boolean save) {
        return closeEditors(getEditorReferences(), save);
    }
