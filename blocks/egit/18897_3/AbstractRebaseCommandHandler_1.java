	private void handleUncommittedChanges(final RebaseOperation rebase,
			final Repository repository, final List<String> files) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				Shell shell = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell();
				CleanupUncomittedChangesDialog cleanupUncomittedChangesDialog = new CleanupUncomittedChangesDialog(
						shell,
						UIText.AbstractRebaseCommandHandler_cleanupDialog_title,
						UIText.AbstractRebaseCommandHandler_cleanupDialog_text,
						repository, files);
				cleanupUncomittedChangesDialog.open();
				if (cleanupUncomittedChangesDialog.shouldContinue()) {
					try {
						execute(repository);
					} catch (ExecutionException e) {
						Activator.logError(e.getMessage(), e);
					}
				}
			}
		});
	}
