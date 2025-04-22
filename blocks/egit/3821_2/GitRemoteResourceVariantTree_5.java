	protected ObjectId getObjectId(DiffEntry diffEntry) {
		return diffEntry.getOldId().toObjectId();
	}

	@Override
	protected ObjectId getObjectId(GitSynchronizeData gsd) {
		return gsd.getSrcRevCommit().getTree();
	}

	@Override
	protected RevCommit getCommitId(GitSynchronizeData gsd) {
		return gsd.getSrcRevCommit();
