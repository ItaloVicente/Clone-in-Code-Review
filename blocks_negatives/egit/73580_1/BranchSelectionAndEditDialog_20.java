	private InputDialog getRefNameInputDialog(String prompt,
			final String refPrefix, String initialValue) {
		InputDialog labelDialog = new InputDialog(getShell(),
				UIText.BranchSelectionAndEditDialog_QuestionNewBranchTitle,
				prompt,
				initialValue, ValidationUtils.getRefNameInputValidator(repo,
						refPrefix, true));
		labelDialog.setBlockOnOpen(true);
		return labelDialog;
	}

