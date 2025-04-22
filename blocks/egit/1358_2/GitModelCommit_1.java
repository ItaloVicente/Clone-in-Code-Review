	private void calculateKind(ObjectId baseId, ObjectId remoteId) {
		prepareCommitObject(baseCommit);
		prepareCommitObject(remoteCommit);
		Date remoteCommitTime = remoteCommit.getCommitterIdent().getWhen();
		Date baseCommitTime;
		if (baseCommit != null)
			baseCommitTime = baseCommit.getCommitterIdent().getWhen();
