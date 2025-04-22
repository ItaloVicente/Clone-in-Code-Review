	private void setBloomFilter(RevCommit commit
			throws MissingObjectException {
		ObjectToCommitData oc = commitDataMap.get(commit);
		if (oc == null) {
			throw new MissingObjectException(commit
		}
		oc.setBloomFilter(filter);
	}

