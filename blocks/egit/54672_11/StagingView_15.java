			service.showBusyForFamily(
					org.eclipse.egit.core.JobFamilies.INDEX_DIFF_CACHE_UPDATE);
	}

	private boolean commitAndPushEnabled(boolean commitEnabled) {
		return commitEnabled
				&& !currentRepository.getRepositoryState().isRebasing();
	}

	private void updateIgnoreErrorsButtonVisibility() {
		boolean visible = getPreferenceStore()
				.getBoolean(UIPreferences.WARN_BEFORE_COMMITTING)
				&& getPreferenceStore().getBoolean(UIPreferences.BLOCK_COMMIT);
		showControl(ignoreErrors, visible);
		ignoreErrors.getParent().layout(true);
	}

	private int getProblemsSeverity() {
		int result = IProblemDecoratable.SEVERITY_NONE;
		StagingViewContentProvider stagedContentProvider = getContentProvider(
				stagedViewer);
		StagingEntry[] entries = stagedContentProvider.getStagingEntries();
		for (StagingEntry entry : entries) {
			if (entry.getProblemSeverity() >= IMarker.SEVERITY_WARNING) {
				if (result < entry.getProblemSeverity()) {
					result = entry.getProblemSeverity();
				}
			}
		}
		return result;
	}

	private void updateCommitButtons() {
		IndexDiffData indexDiff;
		if (cacheEntry != null) {
			indexDiff = cacheEntry.getIndexDiff();
		} else {
			indexDiff = doReload(currentRepository);
		}
		boolean indexDiffAvailable = indexDiffAvailable(indexDiff);
		boolean noConflicts = noConflicts(indexDiff);

		boolean commitEnabled = !isCommitBlocked() && noConflicts
				&& indexDiffAvailable;

		boolean commitAndPushEnabled = commitAndPushEnabled(commitEnabled);

		commitButton.setEnabled(commitEnabled);
		commitAndPushButton.setEnabled(commitAndPushEnabled);
