		pm.beginTask(JGitText.get().selectingCommits, ProgressMonitor.UNKNOWN);
		RevWalk rw = new RevWalk(reader);
		rw.setRetainBody(false);
		CommitSelectionHelper selectionHelper = setupTipCommitBitmaps(rw,
				expectedCommitCount, excludeFromBitmapSelection);
		pm.endTask();

		int totCommits = selectionHelper.getCommitCount();
		BlockList<BitmapCommit> selections = new BlockList<>(
				totCommits / recentCommitSpan + 1);
		for (BitmapCommit reuse : selectionHelper.reusedCommits) {
			selections.add(reuse);
		}

		if (totCommits == 0) {
			for (AnyObjectId id : selectionHelper.peeledWants) {
				selections.add(new BitmapCommit(id, false, 0));
