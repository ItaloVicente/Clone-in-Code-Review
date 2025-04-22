	void writeChangedPathCommitGraph() throws Exception {
		GC gc = new GC(db);
		db.getConfig().setBoolean(ConfigConstants.CONFIG_GC_SECTION
				ConfigConstants.CONFIG_KEY_WRITE_COMMIT_GRAPH
		db.getConfig().setBoolean(ConfigConstants.CONFIG_COMMIT_GRAPH_SECTION
				null
		gc.writeCommitGraph();
	}

