	private Boolean showContinueDialogInUI(final IPreferenceStore store,
			final ILaunchConfiguration launchConfiguration) {
		String[] buttons = new String[] { UIText.BranchOperationUI_Continue,
				IDialogConstants.CANCEL_LABEL };
		String message = NLS.bind(
				UIText.BranchOperationUI_RunningLaunchMessage,
				launchConfiguration.getName());
		MessageDialogWithToggle continueDialog = new MessageDialogWithToggle(
				getShell(), UIText.BranchOperationUI_RunningLaunchTitle, null,
				message, MessageDialog.NONE, buttons, 0,
				UIText.BranchOperationUI_RunningLaunchDontShowAgain, false);
		int result = continueDialog.open();
		if (result == IDialogConstants.CANCEL_ID || result == SWT.DEFAULT) {
			return Boolean.TRUE;
		}
		boolean dontWarnAgain = continueDialog.getToggleState();
		if (dontWarnAgain)
			store.setValue(
					UIPreferences.SHOW_RUNNING_LAUNCH_ON_CHECKOUT_WARNING,
					false);
		return Boolean.FALSE;
	}

