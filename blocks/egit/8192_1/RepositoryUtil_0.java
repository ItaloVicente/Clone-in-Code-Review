
	public RevCommit parseHeadCommit(Repository repository) {
		RevWalk walk = null;
		try {
			Ref head = repository.getRef(Constants.HEAD);
			if (head == null || head.getObjectId() == null)
				return null;

			walk = new RevWalk(repository);
			RevCommit commit = walk.parseCommit(head.getObjectId());
			return commit;
		} catch (IOException e) {
			return null;
		} finally {
			if (walk != null)
				walk.release();
		}
	}
