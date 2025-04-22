	@NonNull
	CommitGraph commitGraph() {
		if (commitGraph == null) {
			commitGraph = reader != null ? reader.getCommitGraph().orElse(EMPTY)
					: EMPTY;
		}
		return commitGraph;
	}

