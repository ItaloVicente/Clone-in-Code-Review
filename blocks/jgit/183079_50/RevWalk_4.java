	@NonNull
	CommitGraph commitGraph() {
		if (commitGraph == null) {
			commitGraph = reader != null
					? reader.getCommitGraph().orElse(EMPTY_COMMIT_GRAPH)
					: EMPTY_COMMIT_GRAPH;
		}
		return commitGraph;
	}

