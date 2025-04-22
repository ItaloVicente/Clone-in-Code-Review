			return (IContainer) resource;

		}

		return null;
	}

	protected java.util.List getTypesToImport() {

		return selectedTypes;
	}

	protected void handleContainerBrowseButtonPressed() {
		IPath containerPath = queryForContainer(getSpecifiedContainer(),
				IDEWorkbenchMessages.WizardImportPage_selectFolderLabel,
				IDEWorkbenchMessages.WizardImportPage_selectFolderTitle);

		if (containerPath != null) { // null means user cancelled
			setErrorMessage(null);
			containerNameField.setText(containerPath.makeRelative().toString());
		}
	}

	@Override
