	private boolean handleUncommittedChanges(final Repository repository,
			final List<String> files, Shell shell) {
		CleanupUncomittedChangesDialog cleanupUncomittedChangesDialog = new CleanupUncomittedChangesDialog(
				shell, UIText.AbstractRebaseCommandHandler_cleanupDialog_title,
				UIText.AbstractRebaseCommandHandler_cleanupDialog_text,
				repository, files);
		cleanupUncomittedChangesDialog.open();
		if (cleanupUncomittedChangesDialog.shouldContinue()) {
			final PullOperationUI outerThis = this;
			PullOperationUI pullOperationUI = new PullOperationUI(
					Collections.singleton(repository)) {

				public void done(IJobChangeEvent event) {
					outerThis.results.putAll(this.results);
					int missing = outerThis.tasksToWaitFor.decrementAndGet();
					if (missing == 0)
						outerThis.showResults();
				}
			};
			pullOperationUI.start();
			return true;
		} else
			return false;
	}

	private void showResults() {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				Shell shell = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell();
				showResults(shell);
			}
		});
