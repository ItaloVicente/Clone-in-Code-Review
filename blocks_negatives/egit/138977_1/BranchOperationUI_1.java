				CleanupUncomittedChangesDialog cleanupUncomittedChangesDialog = new CleanupUncomittedChangesDialog(
						shell, UIText.BranchResultDialog_CheckoutConflictsTitle,
						NLS.bind(
								UIText.BranchResultDialog_CheckoutConflictsMessage,
								Repository.shortenRefName(target)),
						repository, result.getConflictList());
				cleanupUncomittedChangesDialog.open();
				if (cleanupUncomittedChangesDialog.shouldContinue()) {
