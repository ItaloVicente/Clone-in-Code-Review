		if (noOpenProjects()) {
			setErrorMessage(IDEWorkbenchMessages.WizardImportPage_noOpenProjects);
			return false;
		}
		return super.determinePageCompletion();
	}

	private boolean noOpenProjects() {
