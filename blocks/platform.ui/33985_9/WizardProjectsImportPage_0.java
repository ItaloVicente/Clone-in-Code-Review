		if (displayConflictWarning && displayInvalidWarning) {
			setMessage(DataTransferMessages.WizardProjectsImportPage_projectsInWorkspaceAndInvalid, WARNING);
		} else if (displayConflictWarning) {
			setMessage(DataTransferMessages.WizardProjectsImportPage_projectsInWorkspace, WARNING);
		} else if (displayInvalidWarning) {
			setMessage(DataTransferMessages.WizardProjectsImportPage_projectsInvalid, WARNING);
