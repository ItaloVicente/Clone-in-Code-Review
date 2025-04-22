		getButton(OK).setEnabled(enabled);
		if (globalBuildButton != null) {
			globalBuildButton.setEnabled(buildNowButton.getSelection());
		}
		if (projectBuildButton != null) {
			projectBuildButton.setEnabled(buildNowButton.getSelection());
		}
	}

	protected void updateBuildRadioEnablement() {
		projectBuildButton.setSelection(!globalBuildButton.getSelection());
	}

	@Override
