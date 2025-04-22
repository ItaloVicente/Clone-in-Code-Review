        createRootDirectoryGroup(parent);
        createFileSelectionGroup(parent);
        createButtonsGroup(parent);
    }

    /**
     * Enable or disable the button group.
     */
    protected void enableButtonGroup(boolean enable) {
        selectTypesButton.setEnabled(enable);
        selectAllButton.setEnabled(enable);
        deselectAllButton.setEnabled(enable);
    }

    /**
     *	Answer a boolean indicating whether the specified source currently exists
     *	and is valid
     */
    protected boolean ensureSourceIsValid() {
        if (new File(getSourceDirectoryName()).isDirectory()) {
