	private boolean findCommit(Repository repo, RevCommit startCommit, RevCommit commitToBeFound)
			throws TeamException {
		RevWalk rw = new RevWalk(repo);
		rw.reset();
		rw.setRevFilter(new RevCommitFilter(commitToBeFound));

		try {
			rw.markStart(startCommit);

			return rw.next() != null;
		} catch (IOException e) {
			throw new TeamException(e.getMessage(), e);
		}

	}

	private final class RevCommitFilter extends RevFilter {

		private final String commitId;

		public RevCommitFilter(RevCommit revCommit) {
			commitId = revCommit.getId().getName();
		}

		@Override
		public boolean include(RevWalk walker, RevCommit cmit)
				throws StopWalkException, MissingObjectException,
				IncorrectObjectTypeException, IOException {
			return cmit.getId().getName().equals(commitId);
		}

		@Override
		public RevFilter clone() {
			return this;
		}
	}

