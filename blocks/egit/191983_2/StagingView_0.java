		UnitOfWork.execute(currentRepository, () -> {

			boolean commitEnabled = noConflicts && indexDiffAvailable
					&& isCommitPossible() && !isCommitBlocked();
			commitButton.setEnabled(commitEnabled);

			final Repository repo = currentRepository;
			pushesHeadOnly = canPushHeadOnly();
			commitAndPushButton
					.setEnabled(
							repo != null && (commitEnabled || pushesHeadOnly)
							&& !repo.getRepositoryState().isRebasing());
			updateCommitAndPush(repo);
		});
	}

	private void updateCommitAndPush(Repository repository) {
		PushMode pushMode = getPushMode(repository);
