				form.setText(getRepositoryName(repo));
			return;
		}
		IndexDiffCacheEntry entry = org.eclipse.egit.core.Activator.getDefault()
				.getIndexDiffCache().getIndexDiffCacheEntry(repo);
		IndexDiffData data = entry == null ? null : entry.getIndexDiff();
		boolean hasConflicts = data != null && !data.getConflicting().isEmpty();
		if (!currentPlan.isRebasingInteractive()) {
			continueItem.setEnabled(!hasConflicts && ResourcePropertyTester
					.testRepositoryState(repo, "canContinueRebase")); //$NON-NLS-1$
			boolean canAbort = ResourcePropertyTester.testRepositoryState(repo,
					"canAbortRebase"); //$NON-NLS-1$
			skipItem.setEnabled(canAbort);
			abortItem.setEnabled(canAbort);
