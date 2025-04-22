	private void openThisVersionInEditor(FileDiff d) {
		ObjectId[] blobs = d.getBlobs();
		ObjectId blob = blobs[blobs.length - 1];
		openInEditor(d.getNewPath(), d.getCommit(), blob);
	}

	private void openPreviousVersionInEditor(FileDiff d) {
		RevCommit commit = d.getCommit().getParent(0);
		ObjectId blob = d.getBlobs()[0];
		openInEditor(d.getOldPath(), commit, blob);
	}

	private void openInEditor(String path, RevCommit commit, ObjectId blob) {
