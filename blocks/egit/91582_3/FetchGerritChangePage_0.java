			CheckoutResult result;
			try {
				result = checkout(textForBranch, progress.newChild(1));
			} catch (OperationCanceledException | InterruptedException e1) {
				return;
			}
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
							checkout(textForBranch, progress.newChild(1));
						} catch (InterruptedException e) {
							Activator.logError(e.getMessage(), e);
