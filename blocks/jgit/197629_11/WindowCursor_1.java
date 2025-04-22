	@Override
	public Optional<CommitGraph> getCommitGraph() {
		if (db.getConfig().get(CoreConfig.KEY).enableCommitGraph()) {
			return db.getCommitGraph();
		}
		return Optional.empty();
	}

