	protected ObjectId getObjectId(DiffEntry diffEntry) {
		return diffEntry.getOldId().toObjectId();
	}

	@Override
	protected RevCommit getCommitId(GitSynchronizeData gsd) {
		return gsd.getDstRevCommit();
