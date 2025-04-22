		InputDialog commitMessageDialog = new InputDialog(shell,
				UIText.StashCreateCommand_titleEnterCommitMessage,
				UIText.StashCreateCommand_messageEnterCommitMessage,
				null, null);
		if (commitMessageDialog.open() != Window.OK)
			return null;
		String message = commitMessageDialog.getValue();
		if (message.length() == 0)
			message = null;

		final StashCreateOperation op = new StashCreateOperation(repo, message);
		Job job = new Job(UIText.StashCreateCommand_jobTitle) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				monitor.beginTask("", 1); //$NON-NLS-1$
				try {
					op.execute(monitor);
					RevCommit commit = op.getCommit();
					if (commit == null)
						showNoChangesToStash(shell);

				} catch (CoreException e) {
					Activator
							.logError(UIText.StashCreateCommand_stashFailed, e);
				}
				return Status.OK_STATUS;
			}
