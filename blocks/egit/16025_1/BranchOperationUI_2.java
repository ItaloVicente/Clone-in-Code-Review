	private String getTargetAskHowToCheckoutRemoteTrackingBranch() {
		String[] buttons = new String[] {
				UIText.BranchOperationUI_CheckoutRemoteTrackingAsLocal,
				UIText.BranchOperationUI_CheckoutRemoteTrackingCommit,
				IDialogConstants.CANCEL_LABEL };
		MessageDialog questionDialog = new MessageDialog(
				getShell(),
				UIText.BranchOperationUI_CheckoutRemoteTrackingTitle,
				null,
				UIText.BranchOperationUI_CheckoutRemoteTrackingQuestion,
				MessageDialog.QUESTION, buttons, 0);
		int result = questionDialog.open();
		if (result == 0) {
			CreateBranchWizard wizard = new CreateBranchWizard(repository,
					target);
			WizardDialog createBranchDialog = new WizardDialog(getShell(),
					wizard);
			createBranchDialog.open();
			return null;
		} else if (result == 1) {
			return target;
		} else {
			return null;
		}
	}

