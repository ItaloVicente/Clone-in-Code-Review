	@NonNull
	Optional<CommitGraph> commitGraph() {
		if (commitGraphLoaded) {
			return commitGraph;
		}
		commitGraph = reader.getCommitGraph();
		commitGraphLoaded = true;
		return commitGraph;
	}

