	private boolean isMergeDuringRebase(RepositoryState state) {
		if (state != RepositoryState.REBASING_INTERACTIVE
				&& state != RepositoryState.REBASING_MERGE)
			return false;
		try {
			return repo.readMergeHeads() != null;
		} catch (IOException e) {
			throw new JGitInternalException(MessageFormat.format(
					JGitText.get().exceptionOccurredDuringReadingOfGIT_DIR
					Constants.MERGE_HEAD
		}
	}

