	@Override
	public Optional<CommitGraph> getCommitGraph() throws IOException {
		for (DfsPackFile pack : db.getPacks()) {
			CommitGraph cg = pack.getCommitGraph(this);
			if (cg != null) {
				return Optional.ofNullable(cg);
			}
		}
		return Optional.empty();
	}

