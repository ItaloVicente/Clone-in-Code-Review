    /**
     * Create a new dialog.
     *
     * @param window the window
     */
    public ProjectPropertyDialogAction(IWorkbenchWindow window) {
        if (window == null) {
            throw new IllegalArgumentException();
        }
        this.workbenchWindow = window;
        setText(IDEWorkbenchMessages.Workbench_projectProperties);
        setToolTipText(IDEWorkbenchMessages.Workbench_projectPropertiesToolTip);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
                IIDEHelpContextIds.PROJECT_PROPERTY_DIALOG_ACTION);
        workbenchWindow.getSelectionService().addSelectionListener(this);
        workbenchWindow.getPartService().addPartListener(this);
    }
