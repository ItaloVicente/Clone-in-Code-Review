	private void prepareTypedElements() {
		if (ancestorElement != null) // other elements should also not be null
			return;

		ComparisonDataSource baseData;
		ComparisonDataSource remoteData;

		RevWalk rw = new RevWalk(repo);
		rw.setRetainBody(true);
		RevCommit baseCommit = null;
		RevCommit remoteCommit = null;
		try {
			if (change.getCommitId() != null)
				baseCommit = rw.parseCommit(change.getCommitId().toObjectId());
			if (change.getRemoteCommitId() != null)
				remoteCommit = rw.parseCommit(change.getRemoteCommitId()
						.toObjectId());
		} catch (IOException e) {
			Activator.logError(e.getMessage(), e);
		} finally {
			rw.dispose();
		}
		if (baseCommit == null && remoteCommit != null)
			baseCommit = remoteCommit; // prevent from NPE for deleted files

		ObjectId localId = extractObjectId(change.getObjectId());
		ObjectId remoteId = extractObjectId(change.getRemoteObjectId());

		if ((getKind() & RIGHT) == RIGHT) {
			baseData = new ComparisonDataSource(baseCommit, localId);
			remoteData = new ComparisonDataSource(remoteCommit, remoteId);
		} else /* getKind() == LEFT */{
			baseData = new ComparisonDataSource(remoteCommit, remoteId);
			remoteData = new ComparisonDataSource(baseCommit, localId);
		}
