	private void updateLimitsCompositeEnablement() {
		boolean enableAll = !allButton.getSelection();
		recursivelySetEnabled(compositeLimits, enableAll);
		updateLimitTextEnablement();
	}

	private void updateLimitTextEnablement() {
		boolean enableAll = !allButton.getSelection();
		boolean useLimits = limitButton.getSelection();
		limitsLabel.setEnabled(enableAll && useLimits);
		limitText.setEnabled(enableAll && useLimits);

	}

