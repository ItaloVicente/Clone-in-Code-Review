		pm.beginTask(JGitText.get().selectingCommits, totCommits);
		int totalWants = selectionHelper.peeledWants.size();

		for (BitmapBuilderEntry entry : selectionHelper.tipCommitBitmaps) {
			BitmapBuilder bitmap = entry.getBuilder();
			int cardinality = bitmap.cardinality();

			List<List<BitmapCommit>> chains =
					new ArrayList<>();

			boolean isActiveBranch = true;
			if (totalWants > excessiveBranchCount
					&& !isRecentCommit(entry.getCommit())) {
				isActiveBranch = false;
