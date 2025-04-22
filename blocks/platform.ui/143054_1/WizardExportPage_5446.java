		IPath containerPath = queryForContainer((IContainer) currentFolder,
				IDEWorkbenchMessages.WizardExportPage_selectResourcesToExport);
		if (containerPath != null) { // null means user cancelled
			String relativePath = containerPath.makeRelative().toString();
			if (!relativePath.toString().equals(resourceNameField.getText())) {
				resetSelectedResources();
				resourceNameField.setText(relativePath);
			}
