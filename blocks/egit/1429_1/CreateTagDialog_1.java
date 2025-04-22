	private RevWalk getRevCommits() {
		RevWalk revWalk = new RevWalk(repo);
		revWalk.sort(RevSort.COMMIT_TIME_DESC, true);
		revWalk.sort(RevSort.BOUNDARY, true);

		try {
			AnyObjectId headId = repo.resolve(Constants.HEAD);
			if (headId != null)
				revWalk.markStart(revWalk.parseCommit(headId));
		} catch (IOException e) {
			Activator.logError(UIText.TagAction_errorWhileGettingRevCommits, e);
			setErrorMessage(UIText.TagAction_errorWhileGettingRevCommits);
