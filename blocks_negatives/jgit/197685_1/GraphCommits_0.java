	RevCommit getCommit(int oidPos) {
		if (oidPos < 0 || oidPos >= sortedCommits.size()) {
			return null;
		}
		return sortedCommits.get(oidPos);
	}

