	@Override
	public CommitGraph getCommitGraph() {
		if (db.getRepository().getConfig().get(CoreConfig.KEY)
				.enableCommitGraph()) {
			return db.getCommitGraph();
		}
		return null;
	}

