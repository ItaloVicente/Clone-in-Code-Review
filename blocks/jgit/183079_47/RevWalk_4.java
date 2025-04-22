		CommitGraph graph = commitGraph().orElse(null);
		if (graph != null) {
			int graphPos = graph.findGraphPosition(id);
			if (graphPos >= 0) {
				return createCommitCG(id
			}
		}
