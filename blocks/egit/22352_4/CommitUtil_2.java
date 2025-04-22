
	public static boolean isCommitInCurrentBranch(RevCommit commit,
			Repository repository) throws IOException {
		return areCommitsInCurrentBranch(Collections.singleton(commit),
				repository);
	}

	public static boolean areCommitsInCurrentBranch(
			Collection<RevCommit> commits, Repository repository)
			throws IOException {
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
