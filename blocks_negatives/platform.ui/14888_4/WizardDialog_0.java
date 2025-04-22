	private void restoreUIState(Map state) {
		restoreEnableState(backButton, state, "back"); //$NON-NLS-1$
		restoreEnableState(nextButton, state, "next"); //$NON-NLS-1$
		restoreEnableState(finishButton, state, "finish"); //$NON-NLS-1$
		restoreEnableState(cancelButton, state, "cancel"); //$NON-NLS-1$
		restoreEnableState(helpButton, state, "help"); //$NON-NLS-1$
