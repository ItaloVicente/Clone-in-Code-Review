        IPath containerPath = queryForContainer((IContainer) currentFolder,
                IDEWorkbenchMessages.WizardExportPage_selectResourcesToExport);
            String relativePath = containerPath.makeRelative().toString();
            if (!relativePath.toString().equals(resourceNameField.getText())) {
                resetSelectedResources();
                resourceNameField.setText(relativePath);
            }
        }
    }

    /**
     * Opens a resource selection dialog and records the user's subsequent
     * resource selections.
     */
    protected void handleResourceDetailsButtonPressed() {
        IAdaptable source = getSourceResource();

        if (source == null) {
			source = ResourcesPlugin.getWorkspace().getRoot();
