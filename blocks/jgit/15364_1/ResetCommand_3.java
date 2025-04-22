	private RevCommit parseCommit(final ObjectId commitId) {
		RevCommit commit;
		RevWalk rw = new RevWalk(repo);
		try {
			commit = rw.parseCommit(commitId);
		} catch (IOException e) {
			throw new JGitInternalException(MessageFormat.format(
					JGitText.get().cannotReadCommit
		} finally {
			rw.release();
		}
		return commit;
	}

	private ObjectId resolveRefToCommitId() {
		try {
		} catch (IOException e) {
			throw new JGitInternalException(
					MessageFormat.format(JGitText.get().cannotRead
					e);
		}
	}

