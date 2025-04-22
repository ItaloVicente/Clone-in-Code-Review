		}
		updateWidgetEnablements();
	}

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

	protected boolean isExportableExtension(String extension) {
		if (selectedTypes == null) {
