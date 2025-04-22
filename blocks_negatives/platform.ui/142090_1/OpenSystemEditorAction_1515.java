    /**
     * Creates a new action that will open system editors on the then-selected file
     * resources.
     *
     * @param page the workbench page in which to open the editor
     */
    public OpenSystemEditorAction(IWorkbenchPage page) {
        super(IDEWorkbenchMessages.OpenSystemEditorAction_text);
        setToolTipText(IDEWorkbenchMessages.OpenSystemEditorAction_toolTip);
        setId(ID);
