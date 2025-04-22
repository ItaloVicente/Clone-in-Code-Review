    private int variableType;

    /**
     * Creates a path variable selection dialog.
     *
     * @param parentShell the parent shell
     * @param variableType the type of variables that are displayed in
     * 	this dialog. <code>IResource.FILE</code> and/or <code>IResource.FOLDER</code>
     * 	logically ORed together.
     */
    public PathVariableSelectionDialog(Shell parentShell, int variableType) {
        super(parentShell);
        setTitle(IDEWorkbenchMessages.PathVariableSelectionDialog_title);
        this.variableType = variableType;
        pathVariablesGroup = new PathVariablesGroup(false, variableType,
                event -> updateExtendButtonState());
        pathVariablesGroup.setSaveVariablesOnChange(true);
        setShellStyle(getShellStyle() | SWT.SHEET);
    }

    @Override
