		final RevCommit leftCommit;
		final ObjectId baseObjectId;
		if (d.getBlobs().length == 2 && !d.getChange().equals(ChangeType.ADD)) {
			leftCommit = c.getParent(0);
			baseObjectId = d.getBlobs()[0];
		} else {
