	private void showError(String message) {
		IWizardPage page = getContainer().getCurrentPage();
		if (page instanceof WizardPage) {
			((WizardPage) page).setErrorMessage(message);
			((WizardPage) page).setPageComplete(false);
		}
	}

