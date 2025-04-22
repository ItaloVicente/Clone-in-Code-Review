	private void initCommitsMap() {
		commitsMap = new HashMap<String, PlotCommit>();
		for (PlotCommit commit : allCommits) {
			commitsMap.put(commit.getId().name(), commit);
		}
	}

