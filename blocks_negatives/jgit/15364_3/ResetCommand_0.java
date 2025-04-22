			final ObjectId commitId;
			try {
				if (commitId == null) {
					throw new JGitInternalException("Invalid ref " + ref
							+ " specified");
				}
			} catch (IOException e) {
				throw new JGitInternalException(
						MessageFormat.format(JGitText.get().cannotRead, ref),
						e);
			}
			RevWalk rw = new RevWalk(repo);
			try {
				commit = rw.parseCommit(commitId);
			} catch (IOException e) {
				throw new JGitInternalException(
						MessageFormat.format(
						JGitText.get().cannotReadCommit, commitId.toString()),
						e);
			} finally {
				rw.release();
