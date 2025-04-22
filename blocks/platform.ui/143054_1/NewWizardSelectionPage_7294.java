	public void advanceToNextPageOrFinish() {
		if (canFlipToNextPage()) {
			getContainer().showPage(getNextPage());
		} else if (canFinishEarly()) {
			if (getWizard().performFinish()) {
				((WizardDialog) getContainer()).close();
			}
		}
