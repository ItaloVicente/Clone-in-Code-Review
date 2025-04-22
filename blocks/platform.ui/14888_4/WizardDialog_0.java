	private void restoreUIState(Map<String, Object> saveState) {
		restoreEnableState(backButton, saveState, "back"); //$NON-NLS-1$
		restoreEnableState(nextButton, saveState, "next"); //$NON-NLS-1$
		restoreEnableState(finishButton, saveState, "finish"); //$NON-NLS-1$
		restoreEnableState(cancelButton, saveState, "cancel"); //$NON-NLS-1$
		restoreEnableState(helpButton, saveState, "help"); //$NON-NLS-1$
		Object pageValue = saveState.get("page"); //$NON-NLS-1$
