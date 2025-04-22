        return projectNameField.getText().trim();
    }

    /**
     * Sets the initial project name that this page will use when
     * created. The name is ignored if the createControl(Composite)
     * method has already been called. Leading and trailing spaces
     * in the name are ignored.
     * Providing the name of an existing project will not necessarily
     * cause the wizard to warn the user.  Callers of this method
     * should first check if the project name passed already exists
     * in the workspace.
     *
     * @param name initial project name for this page
     *
     * @see IWorkspace#validateName(String, int)
     *
     */
    public void setInitialProjectName(String name) {
        if (name == null) {
