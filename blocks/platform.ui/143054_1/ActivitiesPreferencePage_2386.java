	protected void createActivityPromptPref(Composite composite) {
		activityPromptButton = new Button(composite, SWT.CHECK);
		activityPromptButton
				.setText(strings.getProperty(ACTIVITY_PROMPT_BUTTON, ActivityMessages.activityPromptButton));
		activityPromptButton.setToolTipText(
				strings.getProperty(ACTIVITY_PROMPT_BUTTON_TOOLTIP, ActivityMessages.activityPromptToolTip));

		setActivityButtonState();
	}
