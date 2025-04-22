
		if (state == RepositoryState.MERGING_RESOLVED) {
			try {
				parents = repo.readMergeHeads();
			} catch (IOException e) {
				throw new JGitInternalException(MessageFormat.format(
						JGitText.get().exceptionOccuredDuringReadingOfGIT_DIR
						Constants.MERGE_HEAD
			}
			if (message == null) {
				try {
					message = repo.readMergeCommitMsg();
				} catch (IOException e) {
					throw new JGitInternalException(MessageFormat.format(
							JGitText.get().exceptionOccuredDuringReadingOfGIT_DIR
							Constants.MERGE_MSG
				}
			}
		}
		if (message == null)
			throw new NoMessageException(JGitText.get().commitMessageNotSpecified);
