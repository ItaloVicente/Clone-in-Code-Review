		if (operation == Operation.BEGIN) {
			IndexDiffData indexDiffData = org.eclipse.egit.core.Activator
					.getDefault().getIndexDiffCache()
					.getIndexDiffCacheEntry(repository).getIndexDiff();
			List<String> files = new ArrayList<String>();
			files.addAll(indexDiffData.getAdded());
			files.addAll(indexDiffData.getChanged());
			files.addAll(indexDiffData.getModified());
			files.addAll(indexDiffData.getRemoved());
			files.addAll(indexDiffData.getConflicting());

			if (!files.isEmpty()) {
				handleUncommittedChanges(rebase, repository, files);
				return;
			}
		}
		startRebaseJob(rebase, repository, operation);
	}

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
					startRebaseJob(rebase, repository, rebase.getOperation());
				}
			}
		});
	}

	private void startRebaseJob(final RebaseOperation rebase,
			final Repository repository, final RebaseCommand.Operation operation) {
