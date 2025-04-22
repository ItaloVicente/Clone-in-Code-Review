	private boolean shouldCancelBecauseOfRunningLaunches(
			IProgressMonitor monitor) {
		if (!showQuestionsBeforeCheckout) {
			return false;
		}
		final IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		if (!store.getBoolean(
				UIPreferences.SHOW_RUNNING_LAUNCH_ON_CHECKOUT_WARNING)) {
			return false;
		}
		final ILaunchConfiguration launchConfiguration = getRunningLaunchConfiguration(monitor);
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

	private boolean showContinueDialogInUI(final IPreferenceStore store,
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
		if (result == IDialogConstants.CANCEL_ID || result == SWT.DEFAULT)
			return true;
		boolean dontWarnAgain = continueDialog.getToggleState();
		if (dontWarnAgain)
			store.setValue(
					UIPreferences.SHOW_RUNNING_LAUNCH_ON_CHECKOUT_WARNING,
					false);
		return false;
	}

	private ILaunchConfiguration getRunningLaunchConfiguration(
			IProgressMonitor monitor) {
		final ILaunchConfiguration[] result = { null };
		IRunnableWithProgress operation = new IRunnableWithProgress() {

			@Override
			public void run(IProgressMonitor m)
					throws InvocationTargetException, InterruptedException {
				Set<IProject> projects = new HashSet<>(
						Arrays.asList(ProjectUtil.getProjects(repository)));
				result[0] = LaunchFinder.findLaunch(projects, m);
			}
		};
		try {
			if (ModalContext.isModalContextThread(Thread.currentThread())) {
				operation.run(monitor);
			} else {
				ModalContext.run(operation, true, monitor,
						PlatformUI.getWorkbench().getDisplay());
			}
		} catch (InvocationTargetException e) {
		} catch (InterruptedException e) {
		}
		return result[0];
	}

