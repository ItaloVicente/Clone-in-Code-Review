		RevCommit commit;
		ObjectId blob;
		ObjectId[] blobs = d.getBlobs();
		if (d.getChange().equals(ChangeType.DELETE)) {
			commit = d.getCommit().getParent(0);
			blob = blobs[0];
		} else {
			commit = d.getCommit();
			blob = blobs[blobs.length - 1];
		}
