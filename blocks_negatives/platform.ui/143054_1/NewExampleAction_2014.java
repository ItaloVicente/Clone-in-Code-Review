    /**
     * Creates a new action for launching the new project
     * selection wizard.
     *
     * @param window the workbench window to query the current
     * 		selection and shell for opening the wizard.
     */
    public NewExampleAction(IWorkbenchWindow window) {
        super(IDEWorkbenchMessages.NewExampleAction_text);
        if (window == null) {
            throw new IllegalArgumentException();
        }
        this.window = window;
        ISharedImages images = PlatformUI.getWorkbench().getSharedImages();
        setImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD));
        setDisabledImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD_DISABLED));
        setToolTipText(IDEWorkbenchMessages.NewExampleAction_toolTip);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
