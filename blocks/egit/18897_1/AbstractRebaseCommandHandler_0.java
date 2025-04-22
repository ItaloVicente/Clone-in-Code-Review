		if (operation == Operation.BEGIN) {
			try {
				final org.eclipse.jgit.api.Status status = Git.wrap(repository)
						.status().call();
				if (!status.isClean()) {
					handleUncommittedChanges(rebase, repository, status);
					return;
				}
			} catch (NoWorkTreeException e) {
				Activator.logError(e.getMessage(), e);
			} catch (GitAPIException e) {
				Activator.logError(e.getMessage(), e);
			}
		}
		startRebaseJob(rebase, repository, operation);
	}

	private void handleUncommittedChanges(final RebaseOperation rebase,
			final Repository repository,
			final org.eclipse.jgit.api.Status status) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				Shell shell = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell();
				List<String> files = new ArrayList<String>();
				files.addAll(status.getAdded());
				files.addAll(status.getChanged());
				files.addAll(status.getModified());
				files.addAll(status.getRemoved());
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
