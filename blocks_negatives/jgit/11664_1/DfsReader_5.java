	@Override
	public void walkAdviceBeginCommits(RevWalk walk, Collection<RevCommit> roots) {
		wantReadAhead = true;
	}

	@Override
	public void walkAdviceBeginTrees(ObjectWalk ow, RevCommit min, RevCommit max) {
		wantReadAhead = true;
	}

	@Override
	public void walkAdviceEnd() {
		cancelReadAhead();
	}

