
	ObjectId[] getCommits() {
		final Map<String, PlotCommit> commits = commitsMap;
		if (commits == null)
			return new ObjectId[0];
		synchronized (commits) {
			return commits.values().toArray(new ObjectId[commits.size()]);
		}
	}
