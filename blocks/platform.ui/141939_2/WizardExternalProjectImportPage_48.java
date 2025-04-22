		}

		String selectedDirectory = dialog.open();
		if (selectedDirectory != null) {
			previouslyBrowsedDirectory = selectedDirectory;
			locationPathField.setText(previouslyBrowsedDirectory);
			setProjectName(projectFile(previouslyBrowsedDirectory));
		}
	}

	private boolean validatePage() {

		String locationFieldContents = getProjectLocationFieldValue();

		if (locationFieldContents.equals("")) { //$NON-NLS-1$
			setErrorMessage(null);
			setMessage(DataTransferMessages.WizardExternalProjectImportPage_projectLocationEmpty);
			return false;
		}

		IPath path = new Path(""); //$NON-NLS-1$
		if (!path.isValidPath(locationFieldContents)) {
			setErrorMessage(DataTransferMessages.WizardExternalProjectImportPage_locationError);
			return false;
		}

		File projectFile = projectFile(locationFieldContents);
		if (projectFile == null) {
			setErrorMessage(
					NLS.bind(DataTransferMessages.WizardExternalProjectImportPage_notAProject, locationFieldContents));
			return false;
		}
		setProjectName(projectFile);

		if (getProjectHandle().exists()) {
			setErrorMessage(DataTransferMessages.WizardExternalProjectImportPage_projectExistsMessage);
			return false;
		}

		setErrorMessage(null);
		setMessage(null);
		return true;
	}

	private IWorkspace getWorkspace() {
		IWorkspace workspace = IDEWorkbenchPlugin.getPluginWorkspace();
		return workspace;
	}

	private boolean isPrefixOfRoot(IPath locationPath) {
		return Platform.getLocation().isPrefixOf(locationPath);
	}

	private void setProjectName(File projectFile) {

		if (projectFile == null) {
