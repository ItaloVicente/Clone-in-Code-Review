	Optional<CommitGraph> getCommitGraph() {
		if (commitGraphLoaded) {
			return commitGraph;
		}
		commitGraph = reader.getCommitGraph();
		commitGraphLoaded = true;
		return commitGraph;
	}

