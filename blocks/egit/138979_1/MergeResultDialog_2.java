		if (mergeResult.getMergeStatus() == MergeStatus.CHECKOUT_CONFLICT) {
			return new CleanupUncomittedChangesDialog(parentShell,
					UIText.BranchResultDialog_CheckoutConflictsTitle,
					UIText.AbstractRebaseCommandHandler_cleanupDialog_text,
					repository, mergeResult.getCheckoutConflicts(), false);
		} else {
