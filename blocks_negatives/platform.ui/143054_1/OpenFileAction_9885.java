    /**
     * Creates a new action that will open editors on the then-selected file
     * resources. Equivalent to <code>OpenFileAction(page,null)</code>.
     *
     * @param page the workbench page in which to open the editor
     */
    public OpenFileAction(IWorkbenchPage page) {
        this(page, null);
    }
