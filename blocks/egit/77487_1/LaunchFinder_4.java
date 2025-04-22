	public static boolean shouldCancelBecauseOfRunningLaunches(
			Repository repository, IProgressMonitor monitor) {
		return shouldCancelBecauseOfRunningLaunches(
				Collections.singleton(repository), monitor);
	}

	public static boolean shouldCancelBecauseOfRunningLaunches(
			Collection<Repository> repositories, IProgressMonitor monitor) {
		final IPreferenceStore store = Activator.getDefault()
				.getPreferenceStore();
		if (!store.getBoolean(
				UIPreferences.SHOW_RUNNING_LAUNCH_ON_CHECKOUT_WARNING)) {
			return false;
		}
		SubMonitor progress = SubMonitor.convert(monitor);
		final ILaunchConfiguration launchConfiguration = getRunningLaunchConfiguration(
				repositories,
				progress);
		if (launchConfiguration != null) {
			final boolean[] dialogResult = new boolean[1];
			PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
				@Override
				public void run() {
					dialogResult[0] = showContinueDialogInUI(store,
							launchConfiguration);
				}
			});
			return dialogResult[0];
		}
		return false;
	}

	private static boolean showContinueDialogInUI(final IPreferenceStore store,
			final ILaunchConfiguration launchConfiguration) {
		String[] buttons = new String[] { UIText.BranchOperationUI_Continue,
				IDialogConstants.CANCEL_LABEL };
		String message = NLS.bind(UIText.LaunchFinder_RunningLaunchMessage,
				launchConfiguration.getName()) + ' '
				+ UIText.LaunchFinder_ContinueQuestion;
		MessageDialogWithToggle continueDialog = new MessageDialogWithToggle(
				PlatformUI.getWorkbench().getModalDialogShellProvider()
						.getShell(),
				UIText.LaunchFinder_RunningLaunchTitle, null,
				message, MessageDialog.NONE, buttons, 0,
				UIText.LaunchFinder_RunningLaunchDontShowAgain, false);
		int result = continueDialog.open();
		if (result == IDialogConstants.CANCEL_ID || result == SWT.DEFAULT)
			return true;
		boolean dontWarnAgain = continueDialog.getToggleState();
		if (dontWarnAgain)
			store.setValue(
					UIPreferences.SHOW_RUNNING_LAUNCH_ON_CHECKOUT_WARNING,
					false);
		return false;
	}

