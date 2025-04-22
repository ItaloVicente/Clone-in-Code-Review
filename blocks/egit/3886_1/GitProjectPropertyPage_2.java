	private RepositoryCommit getCommit(Repository repository, ObjectId objectId) {
		RevWalk walk = new RevWalk(repository);
		try {
			RevCommit commit = walk.parseCommit(objectId);
			for (RevCommit parent : commit.getParents())
				walk.parseBody(parent);
			return new RepositoryCommit(repository, commit);
		} catch (IOException e) {
			return null;
		} finally {
			walk.release();
		}
	}
