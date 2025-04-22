	@NonNull
	Optional<CommitGraph> commitGraph() {
		if (commitGraphLoaded) {
			return commitGraph;
		}
		if (reader != null) {
			commitGraph = reader.getCommitGraph();
		} else {
			commitGraph = Optional.empty();
		}
		commitGraphLoaded = true;
		return commitGraph;
	}

