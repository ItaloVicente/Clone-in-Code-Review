		try (TreeWalk w = TreeWalk.forPath(db, gitPath, commit.getTree())) {
			if (w == null) {
				return null;
			}
			CheckoutMetadata metadata = new CheckoutMetadata(
					w.getEolStreamType(),
					w.getFilterCommand(Constants.ATTR_FILTER_TYPE_SMUDGE));
			if (blobId == null) {
				blobId = w.getObjectId(0);
			}
			return GitFileRevision.inCommit(db, commit, gitPath, blobId,
					metadata);
