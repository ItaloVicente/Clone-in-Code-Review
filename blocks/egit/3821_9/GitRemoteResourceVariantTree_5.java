	protected ObjectId getObjectId(DiffEntry diffEntry) {
		return diffEntry.getNewId().toObjectId();
	}

	@Override
	protected RevCommit getCommitId(GitSynchronizeData gsd) {
		return gsd.getSrcRevCommit();
