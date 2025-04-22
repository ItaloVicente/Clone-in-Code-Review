
	private RevCommit parseCommit(final Ref branch)
			throws MissingObjectException
			IOException {
		final RevWalk rw = new RevWalk(db);
		final RevCommit commit;
		try {
			commit = rw.parseCommit(branch.getObjectId());
		} finally {
			rw.release();
		}
		return commit;
	}
