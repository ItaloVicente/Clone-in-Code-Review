		List<FileDiff> unstagedDiffs = new ArrayList<FileDiff>();

		RevCommit stagedCommit = getCommit().getRevCommit().getParent(
				PARENT_COMMIT_STAGED);
		List<FileDiff> workingDirDiffs = asList(getCommit().getDiffs(
				stagedCommit));
		unstagedDiffs.addAll(workingDirDiffs);

