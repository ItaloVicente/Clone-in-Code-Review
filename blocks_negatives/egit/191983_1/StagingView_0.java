		boolean commitEnabled = noConflicts && indexDiffAvailable
				&& isCommitPossible() && !isCommitBlocked();
		commitButton.setEnabled(commitEnabled);

		final Repository repo = currentRepository;
		commitAndPushButton.setEnabled(
				repo != null && (commitEnabled || canPushHeadOnly())
						&& !repo.getRepositoryState().isRebasing());
		PushMode pushMode = getPushMode();
