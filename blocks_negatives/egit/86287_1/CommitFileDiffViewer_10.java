		final String np = d.getNewPath();
		final String op = d.getOldPath();
		final RevCommit c = d.getCommit();

		final RevCommit oldCommit;
		final ObjectId oldObjectId;
		if (d.getBlobs().length == 2 && !d.getChange().equals(ChangeType.ADD)) {
			oldCommit = c.getParent(0);
			oldObjectId = d.getBlobs()[0];
		} else {
			oldCommit = null;
			oldObjectId = null;
		}

		final RevCommit newCommit;
		final ObjectId newObjectId;
		if (d.getChange().equals(ChangeType.DELETE)) {
			newCommit = null;
			newObjectId = null;
