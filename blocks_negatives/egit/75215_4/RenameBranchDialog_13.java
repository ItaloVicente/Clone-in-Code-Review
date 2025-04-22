	private InputDialog getRefNameInputDialog(String prompt,
			final String refPrefix, String initialValue) {
		InputDialog branchNameDialog = new InputDialog(
				getShell(),
				UIText.RenameBranchDialog_RenameBranchDialogNewNameInputWindowTitle,
				prompt, initialValue, ValidationUtils.getRefNameInputValidator(
						repo, refPrefix, true));
		branchNameDialog.setBlockOnOpen(true);
		return branchNameDialog;
	}

