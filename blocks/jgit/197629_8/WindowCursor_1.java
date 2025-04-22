	@Override
	public Optional<CommitGraph> getCommitGraph() {
		if (db.getConfig().get(CoreConfig.KEY).enableCommitGraph()) {
			return Optional.ofNullable(db.getCommitGraph());
		}
		return Optional.empty();
	}

