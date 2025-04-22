		setWindowTitle(pushMode);
	}

	private void setWindowTitle(boolean pushMode) {
		final String title;
		if (pushMode)
			title = UIText.RefSpecWizard_pushTitle;
		else
			title = UIText.RefSpecWizard_fetchTitle;
		setWindowTitle(title);
