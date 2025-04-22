        projectNameField.addListener(SWT.Modify, nameModifyListener);
        BidiUtils.applyBidiProcessing(projectNameField, BidiUtils.BTD_DEFAULT);
    }


    /**
     * Returns the current project location path as entered by
     * the user, or its anticipated initial value.
     * Note that if the default has been returned the path
     * in a project description used to create a project
     * should not be set.
     *
     * @return the project location path or its anticipated initial value.
     */
    public IPath getLocationPath() {
        return new Path(locationArea.getProjectLocation());
    }

    /**
    /**
     * Returns the current project location URI as entered by
     * the user, or <code>null</code> if a valid project location
     * has not been entered.
     *
     * @return the project location URI, or <code>null</code>
     * @since 3.2
     */
    public URI getLocationURI() {
    	return locationArea.getProjectLocationURI();
    }

    /**
	 * Creates a project resource handle for the current project name field
	 * value. The project handle is created relative to the workspace root.
