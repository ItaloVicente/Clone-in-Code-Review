    /**
     * Constructor for ExternalProjectImportWizard.
     *
     * @param initialPath Default path for wizard to import
     * @since 3.5
     */
    public ExternalProjectImportWizard(String initialPath)
    {
        super();
        this.initialPath = initialPath;
        setNeedsProgressMonitor(true);
        IDialogSettings workbenchSettings = IDEWorkbenchPlugin.getDefault()
        		.getDialogSettings();
