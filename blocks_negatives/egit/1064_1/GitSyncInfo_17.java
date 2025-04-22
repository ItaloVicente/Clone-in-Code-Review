	private int calculateDescBasedOnGitCommits(GitBlobResourceVariant baseBlob,
			GitBlobResourceVariant remoteBlob) {

		RevCommitList<RevCommit> baseList = baseBlob.getCommitList();
		RevCommitList<RevCommit> remoteList = remoteBlob.getCommitList();
		RevCommit recentRemoteCommit = remoteList.get(0);
		RevCommit recentBaseCommit = baseList.get(0);

		for (RevCommit baseCommit : baseList) {
			if (recentRemoteCommit.name().equals(baseCommit.name())) {
				return OUTGOING | CHANGE;
			}
		}

		for (RevCommit remoteCommit : remoteList) {
			if (recentBaseCommit.name().equals(remoteCommit.name())) {
				return INCOMING | CHANGE;
			}
		}

		return CONFLICTING | CHANGE;
	}
