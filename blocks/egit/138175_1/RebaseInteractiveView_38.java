				form.setText(getRepositoryName(repo));
			return;
		}
		IndexDiffCacheEntry entry = org.eclipse.egit.core.Activator.getDefault()
				.getIndexDiffCache().getIndexDiffCacheEntry(repo);
		IndexDiffData data = entry == null ? null : entry.getIndexDiff();
		boolean hasConflicts = data != null && !data.getConflicting().isEmpty();
		if (!currentPlan.isRebasingInteractive()) {
			RepositoryState state = repo.getRepositoryState();
			continueItem.setEnabled(!hasConflicts
					&& ResourcePropertyTester.canContinueRebase(state));
			boolean canAbort = ResourcePropertyTester.canAbortRebase(state);
			skipItem.setEnabled(canAbort);
			abortItem.setEnabled(canAbort);
