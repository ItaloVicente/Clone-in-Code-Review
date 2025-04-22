    	}
    	updateWidgetEnablements();
    }

   	/**
     * Initializes the specified operation appropriately.
     */
    protected void initializeOperation(ImportOperation op) {
        op.setCreateContainerStructure(false);
        op.setOverwriteResources(overwriteExistingResourcesCheckbox
                .getSelection());
        if (createLinksInWorkspaceButton != null && createLinksInWorkspaceButton.getSelection()) {
        	op.setCreateLinks(true);
	        op.setVirtualFolders(createVirtualFoldersButton
	                .getSelection());
	        if (relativePathVariableGroup.getSelection())
	        	op.setRelativeVariable(pathVariable);
        }
    }

    /**
     * Returns whether the extension provided is an extension that
     * has been specified for export by the user.
     *
     * @param extension the resource name
     * @return <code>true</code> if the resource name is suitable for export based
     *   upon its extension
     */
    protected boolean isExportableExtension(String extension) {
        if (selectedTypes == null) {
