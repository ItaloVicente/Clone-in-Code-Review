	/**
	 * Returns whether the commits are on the current branch, ie. if they are
	 * reachable from the current HEAD.
	 *
	 * @param commits
	 *            the commits to check
	 * @param repository
	 *            the repository
	 * @return true if the commits are reachable from HEAD
	 * @throws IOException
	 *             if there is an I/O error
	 */
	private boolean areCommitsInCurrentBranch(Collection<RevCommit> commits,
			Repository repository) throws IOException {

		RevWalk walk = new RevWalk(repository);
		ObjectId headCommitId = repository.resolve(Constants.HEAD);
		RevCommit headCommit = walk.parseCommit(headCommitId);

		for (final RevCommit commit : commits) {
			walk.reset();
			walk.markStart(headCommit);

			RevFilter revFilter = new RevFilter() {
				@Override
				public boolean include(RevWalk walker, RevCommit cmit)
						throws StopWalkException, MissingObjectException,
						IncorrectObjectTypeException, IOException {

					return cmit.equals(commit);
				}

				@Override
				public RevFilter clone() {
					return null;
				}
			};
			walk.setRevFilter(revFilter);

			if (walk.next() == null)
				return false;
		}
		return true;
	}

