				if (!repository.getRepositoryState().equals(
						RepositoryState.SAFE)) {
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							Shell shell = PlatformUI.getWorkbench()
									.getActiveWorkbenchWindow().getShell();
							MessageDialog
									.openWarning(
											shell,
											UIText.RebaseHelper_NestedRebaseWarningTitle,
											UIText.RebaseHelper_WarningNestedRebaseRejected);
						}
					});
					return new Status(IStatus.WARNING, Activator.getPluginId(),
							UIText.RebaseHelper_WarningNestedRebaseRejected);
				}
