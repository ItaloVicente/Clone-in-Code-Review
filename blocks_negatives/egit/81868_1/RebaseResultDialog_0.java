		if (result.getStatus() == Status.ABORTED) {
			return;
		}

		boolean shouldShow = result.getStatus() == Status.STOPPED
				|| result.getStatus() == Status.STASH_APPLY_CONFLICTS
				|| Activator.getDefault().getPreferenceStore().getBoolean(
						UIPreferences.SHOW_REBASE_CONFIRM);

		if(result.getStatus() == Status.CONFLICTS) {
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					Shell shell = PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell();
					new CheckoutConflictDialog(shell, repository, result.getConflicts()).open();
				}
			});

			return;
		}

		if (!shouldShow) {
			Activator.getDefault().getLog().log(
					new org.eclipse.core.runtime.Status(IStatus.INFO, Activator
							.getPluginId(), NLS.bind(
							UIText.RebaseResultDialog_StatusLabel, result
									.getStatus().name())));
