	@Override
	public CommitGraph getCommitGraph() {
		if (db.getConfig().get(CoreConfig.KEY).enableCommitGraph()) {
			return db.getCommitGraph();
		}
		return null;
	}

