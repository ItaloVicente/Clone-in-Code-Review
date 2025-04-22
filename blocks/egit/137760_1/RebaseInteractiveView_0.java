				form.setText(getRepositoryName(repo));
			return;
		}
		IndexDiffCacheEntry entry = org.eclipse.egit.core.Activator.getDefault()
				.getIndexDiffCache().getIndexDiffCacheEntry(repo);
		IndexDiffData data = entry == null ? null : entry.getIndexDiff();
		boolean hasConflicts = data != null && !data.getConflicting().isEmpty();
		if (!currentPlan.isRebasingInteractive()) {
			continueItem.setEnabled(ResourcePropertyTester.testRepositoryState(
					repo, "canContinueRebase") && !hasConflicts); //$NON-NLS-1$
			skipItem.setEnabled(ResourcePropertyTester
					.testRepositoryState(repo, "canAbortRebase")); //$NON-NLS-1$
			abortItem.setEnabled(ResourcePropertyTester
					.testRepositoryState(repo, "canAbortRebase")); //$NON-NLS-1$
