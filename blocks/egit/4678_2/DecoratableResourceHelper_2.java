
	static String getBranchStatus(Repository repo) throws IOException {
		String branchName = repo.getBranch();
		if (branchName == null)
			return null;

		BranchTrackingStatus status = BranchTrackingStatus.of(repo, branchName);
		if (status == null)
			return null;

		if (status.getAheadCount() == 0 && status.getBehindCount() == 0)
			return null;

		String formattedStatus = GitLabelProvider.formatBranchTrackingStatus(status);
		return formattedStatus;
	}
