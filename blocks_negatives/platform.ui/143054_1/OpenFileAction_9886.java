    /**
     * Creates a new action that will open instances of the specified editor on
     * the then-selected file resources.
     *
     * @param page the workbench page in which to open the editor
     * @param descriptor the editor descriptor, or <code>null</code> if unspecified
     */
    public OpenFileAction(IWorkbenchPage page, IEditorDescriptor descriptor) {
        super(page);
        setText(descriptor == null ? IDEWorkbenchMessages.OpenFileAction_text : descriptor.getLabel());
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
