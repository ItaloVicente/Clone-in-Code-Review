		}
	}

	void setLocationForSelection() {
		locationArea.updateProjectName(getProjectNameFieldValue());
	}

	protected boolean validatePage() {
		IWorkspace workspace = IDEWorkbenchPlugin.getPluginWorkspace();

		String projectFieldContents = getProjectNameFieldValue();
		if (projectFieldContents.equals("")) { //$NON-NLS-1$
			setErrorMessage(null);
			setMessage(IDEWorkbenchMessages.WizardNewProjectCreationPage_projectNameEmpty);
			return false;
		}

		IStatus nameStatus = workspace.validateName(projectFieldContents, IResource.PROJECT);
		if (!nameStatus.isOK()) {
			setErrorMessage(nameStatus.getMessage());
			return false;
		}

		IProject handle = getProjectHandle();
		if (handle.exists()) {
			setErrorMessage(IDEWorkbenchMessages.WizardNewProjectCreationPage_projectExistsMessage);
			return false;
		}

		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(getProjectNameFieldValue());
