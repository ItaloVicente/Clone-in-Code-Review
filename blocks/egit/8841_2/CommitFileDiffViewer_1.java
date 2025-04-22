		final RevCommit rightCommit;
		final ObjectId rightObjectId;
		if (d.getChange().equals(ChangeType.DELETE)) {
			rightCommit = null;
			rightObjectId = null;
		} else {
			rightCommit = c;
			rightObjectId = d.getBlobs()[1];
		}
