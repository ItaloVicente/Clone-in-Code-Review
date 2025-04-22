			Collections.sort(files);
			CleanupUncomittedChangesDialog cleanupUncomittedChangesDialog = new CleanupUncomittedChangesDialog(
					shell,
					dialogTitle,
					UIText.AbstractRebaseCommandHandler_cleanupDialog_text,
					repo, files);
			cleanupUncomittedChangesDialog.open();
			return cleanupUncomittedChangesDialog.shouldContinue();
		} else
