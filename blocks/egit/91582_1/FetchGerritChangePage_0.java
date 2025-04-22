			final CheckoutResult result = checkout(textForBranch);
			if (result.getStatus() == Status.CONFLICTS) {
				PlatformUI.getWorkbench().getDisplay().asyncExec(() -> {
					Shell shell = PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell();
					CleanupUncomittedChangesDialog cleanupUncomittedChangesDialog = new CleanupUncomittedChangesDialog(
							shell,
							UIText.BranchResultDialog_CheckoutConflictsTitle,
							UIText.AbstractRebaseCommandHandler_cleanupDialog_text,
							repository, result.getConflictList());
					cleanupUncomittedChangesDialog.open();
					if (cleanupUncomittedChangesDialog.shouldContinue()) {
						try {
							checkout(textForBranch);
						} catch (GitAPIException e) {
							Activator.logError(e.getMessage(), e);
