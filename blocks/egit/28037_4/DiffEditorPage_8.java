	protected FileDiff[] getDiffs(RepositoryCommit commit) {
		List<FileDiff> diffResult = new ArrayList<FileDiff>();

		diffResult.addAll(asList(commit.getDiffs()));

		if (commit.getRevCommit().getParentCount() > 2) {
			RevCommit untrackedCommit = commit.getRevCommit().getParent(
					StashEditorPage.PARENT_COMMIT_UNTRACKED);
			diffResult.addAll(asList(new RepositoryCommit(commit
					.getRepository(), untrackedCommit).getDiffs()));
		}
		Collections.sort(diffResult, FileDiff.PATH_COMPARATOR);
		return diffResult.toArray(new FileDiff[0]);
	}

