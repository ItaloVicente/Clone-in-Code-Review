		CleanupUncomittedChangesDialog cleanupUncomittedChangesDialog = new CleanupUncomittedChangesDialog(
				shell,
				MessageFormat
						.format(UIText.AbstractRebaseCommandHandler_cleanupDialog_title,
								repoName),
				UIText.AbstractRebaseCommandHandler_cleanupDialog_text,
				repository, files);
		cleanupUncomittedChangesDialog.open();
		if (cleanupUncomittedChangesDialog.shouldContinue()) {
