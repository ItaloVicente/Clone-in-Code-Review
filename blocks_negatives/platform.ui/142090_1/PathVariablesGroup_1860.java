        initTemporaryState();
    }

    /**
     * Creates a new PathVariablesGroup.
     *
     * @param multiSelect create a multi select tree
     * @param variableType the type of variables that are displayed in
     * 	the widget group. <code>IResource.FILE</code> and/or <code>IResource.FOLDER</code>
     * 	logically ORed together.
     * @param selectionListener listener notified when the selection changes
     * 	in the variables list.
     */
    public PathVariablesGroup(boolean multiSelect, int variableType,
            Listener selectionListener) {
        this(multiSelect, variableType);
        this.selectionListener = selectionListener;
    }

    /**
     * Opens a dialog for creating a new variable.
     */
    private void addNewVariable() {
        PathVariableDialog dialog = new PathVariableDialog(shell,
                PathVariableDialog.NEW_VARIABLE, variableType,
                pathVariableManager, tempPathVariables.keySet());

        dialog.setResource(currentResource);
        if (dialog.open() == Window.CANCEL) {
