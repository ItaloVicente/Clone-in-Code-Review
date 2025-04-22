	private void gcWithCommitGraph() throws IOException {
		DfsGarbageCollector gc = new DfsGarbageCollector(repo);
		gc.setWriteCommitGraph(true);
		run(gc);
	}

