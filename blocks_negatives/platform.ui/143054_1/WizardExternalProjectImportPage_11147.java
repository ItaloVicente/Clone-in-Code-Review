                handleLocationBrowseButtonPressed();
            }
        });

        locationPathField.addListener(SWT.Modify, locationModifyListener);
    }

    /**
     * Returns the current project location path as entered by
     * the user, or its anticipated initial value.
     *
     * @return the project location path, its anticipated initial value, or <code>null</code>
     *   if no project location path is known
     */
    public IPath getLocationPath() {

        return new Path(getProjectLocationFieldValue());
    }

    /**
     * Creates a project resource handle for the current project name field value.
     * <p>
     * This method does not create the project resource; this is the responsibility
     * of <code>IProject::create</code> invoked by the new project resource wizard.
     * </p>
     *
     * @return the new project resource handle
     */
    public IProject getProjectHandle() {
        return ResourcesPlugin.getWorkspace().getRoot().getProject(
                getProjectName());
    }

    /**
     * Returns the current project name as entered by the user, or its anticipated
     * initial value.
     *
     * @return the project name, its anticipated initial value, or <code>null</code>
     *   if no project name is known
     */
    public String getProjectName() {
        return getProjectNameFieldValue();
    }

    /**
     * Returns the value of the project name field
     * with leading and trailing spaces removed.
     *
     * @return the project name in the field
     */
    private String getProjectNameFieldValue() {
        if (projectNameField == null) {
