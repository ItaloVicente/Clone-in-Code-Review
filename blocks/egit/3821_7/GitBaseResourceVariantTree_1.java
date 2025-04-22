	protected ObjectId getObjectId(DiffEntry diffEntry) {
		return diffEntry.getNewId().toObjectId();
	}

	@Override
	protected ObjectId getObjectId(GitSynchronizeData gsd) {
		return gsd.getDstRevCommit().getTree();
	}

	@Override
	protected RevCommit getCommitId(GitSynchronizeData gsd) {
		return gsd.getDstRevCommit();
