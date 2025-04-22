
	static String getBranchStatus(Repository repo) throws IOException {
		String localName = repo.getBranch();
		if (localName == null)
			return null;

		String trackingName = getTrackingBranch(repo, localName);
		if (trackingName == null)
			return null;

		Ref tracking = repo.getRef(trackingName);
		if (tracking == null)
			return null;

		Ref local = repo.getRef(localName);
		if (local == null)
			return null;

		RevWalk walk = new RevWalk(repo.newObjectReader());

		RevCommit localCommit = walk.parseCommit(local.getObjectId());
		RevCommit trackingCommit = walk.parseCommit(tracking.getObjectId());

		walk.setRevFilter(RevFilter.MERGE_BASE);
		walk.markStart(localCommit);
		walk.markStart(trackingCommit);
		RevCommit mergeBase = walk.next();
		if (mergeBase == null)
			return null;

		int localCount = count(walk, localCommit, mergeBase);
		int trackingCount = count(walk, trackingCommit, mergeBase);

		if (localCount == 0 && trackingCount == 0)
			return null;

		StringBuilder sb = new StringBuilder();
		if (localCount != 0) {
			sb.append(" "); //$NON-NLS-1$
			sb.append("↑"); //$NON-NLS-1$
			sb.append(localCount);
		}
		if (trackingCount != 0) {
			sb.append(" "); //$NON-NLS-1$
			sb.append("↓"); //$NON-NLS-1$
			sb.append(trackingCount);
		}
		return sb.toString();
	}

	private static String getTrackingBranch(Repository repo, String branchName) {
		StoredConfig config = repo.getConfig();
		String remoteName = config.getString(ConfigConstants.CONFIG_BRANCH_SECTION,
				branchName, ConfigConstants.CONFIG_KEY_REMOTE);
		String mergeRef = config.getString(ConfigConstants.CONFIG_BRANCH_SECTION,
				branchName, ConfigConstants.CONFIG_KEY_MERGE);
		if (remoteName == null || mergeRef == null)
			return null;

		String remoteBranchName = Repository.shortenRefName(mergeRef);
		String trackingRefName = Constants.R_REMOTES +
				remoteName + "/" + remoteBranchName;  //$NON-NLS-1$
		return trackingRefName;
	}

	private static int count(RevWalk walk, RevCommit start, RevCommit base) throws IOException {
		walk.reset();
		walk.setRevFilter(RevFilter.ALL);
		walk.markStart(start);
		walk.markUninteresting(base);

		int count = 0;
		for (RevCommit c = walk.next(); c != null; c = walk.next())
			count++;
		return count;
	}
