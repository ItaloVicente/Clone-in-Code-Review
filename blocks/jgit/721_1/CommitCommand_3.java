
		if (state == RepositoryState.MERGING_RESOLVED) {
			try {
				parents = repo.readMergeHeads();
			} catch (IOException e) {
				throw new JGitInternalException(
						"Exceptions occured during reading of $GIT_DIR/"
								+ Constants.MERGE_HEAD
			}
			if (message == null) {
				try {
					message = repo.readMergeCommitMsg();
				} catch (IOException e) {
					throw new JGitInternalException(
							"Exceptions occured during reading of $GIT_DIR/"
									+ Constants.MERGE_MSG
				}
			}
		}
		if (message == null) {
			throw new NoMessageException("commit message not specified");
		}
