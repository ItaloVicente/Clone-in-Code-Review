	private void handlePullResults(final Map<Repository, Object> resultsMap,
			Shell shell) {
		for (Entry<Repository, Object> entry : resultsMap.entrySet()) {
			Object result = entry.getValue();
			if (result instanceof PullResult) {
				PullResult pullResult = (PullResult) result;
				if (pullResult.getRebaseResult() != null
						&& RebaseResult.Status.UNCOMMITTED_CHANGES == pullResult
								.getRebaseResult().getStatus()) {
					handleUncommittedChanges(entry.getKey(), pullResult
							.getRebaseResult().getUncommittedChanges(), shell);
				}
			}
		}

		if (tasksToWaitFor.decrementAndGet() == 0 && !results.isEmpty())
			showResults(shell);
	}

	private void handleUncommittedChanges(final Repository repository,
			final List<String> files, Shell shell) {
		String repoName = Activator.getDefault().getRepositoryUtil()
				.getRepositoryName(repository);
		CleanupUncomittedChangesDialog cleanupUncomittedChangesDialog = new CleanupUncomittedChangesDialog(
				shell,
				MessageFormat
						.format(UIText.AbstractRebaseCommandHandler_cleanupDialog_title,
								repoName),
				UIText.AbstractRebaseCommandHandler_cleanupDialog_text,
				repository, files);
		cleanupUncomittedChangesDialog.open();
		if (cleanupUncomittedChangesDialog.shouldContinue()) {
			final PullOperationUI parentOperation = this;
			final PullOperationUI pullOperationUI = new PullOperationUI(
					Collections.singleton(repository));
			tasksToWaitFor.incrementAndGet();
			pullOperationUI.start(new JobChangeAdapter() {

				@Override
				public void done(IJobChangeEvent event) {
					parentOperation.results.putAll(pullOperationUI.results);
					int missing = parentOperation.tasksToWaitFor.decrementAndGet();
					if (missing == 0)
						parentOperation.showResults();
				}
			});
		}
	}

	private void showResults() {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				Shell shell = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell();
				showResults(shell);
			}
		});
