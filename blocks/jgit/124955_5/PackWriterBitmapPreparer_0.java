		try (RevWalk rw = new RevWalk(reader);
				RevWalk rw2 = new RevWalk(reader)) {
			pm.beginTask(JGitText.get().selectingCommits
					ProgressMonitor.UNKNOWN);
			rw.setRetainBody(false);
			CommitSelectionHelper selectionHelper = captureOldAndNewCommits(rw
					expectedCommitCount
			pm.endTask();

			int newCommits = selectionHelper.getCommitCount();
			BlockList<BitmapCommit> selections = new BlockList<>(
					selectionHelper.reusedCommits.size()
							+ newCommits / recentCommitSpan + 1);
			for (BitmapCommit reuse : selectionHelper.reusedCommits) {
				selections.add(reuse);
