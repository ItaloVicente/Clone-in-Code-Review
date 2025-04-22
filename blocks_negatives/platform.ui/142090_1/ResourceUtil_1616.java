    }

    /**
     * Returns the editor in the given page whose input represents the given file,
     * or <code>null</code> if there is no such editor.
     *
     * @param page the workbench page
     * @param file the file
     * @return the matching editor, or <code>null</code>
     */
    public static IEditorPart findEditor(IWorkbenchPage page, IFile file) {
        IEditorPart editor = page.findEditor(new FileEditorInput(file));
        if (editor != null) {
            return editor;
        }
