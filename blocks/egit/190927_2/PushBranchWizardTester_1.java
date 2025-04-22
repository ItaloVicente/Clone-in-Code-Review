	public void prev() {
		wizard.button(IDialogConstants.BACK_LABEL).click();
	}

	public boolean canFinish() {
		return wizard.button(UIText.PushBranchWizard_pushButton).isEnabled();
	}

