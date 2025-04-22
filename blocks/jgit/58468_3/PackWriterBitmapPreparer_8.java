	private boolean isRecentCommit(RevCommit revCommit) {
		return revCommit.getCommitTime() > inactiveBranchTimestamp;
	}

	private CommitSelectionHelper setupTipCommitBitmaps(RevWalk rw
			int expectedCommitCount) throws IncorrectObjectTypeException
					IOException
		BitmapBuilder reuse = commitBitmapIndex.newBitmapBuilder();
		List<BitmapCommit> reuseCommits = new ArrayList<BitmapCommit>();
