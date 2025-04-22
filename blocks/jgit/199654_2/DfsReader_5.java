	@Override
	public Optional<CommitGraph> getCommitGraph() {
		try {
			for (DfsPackFile pack : db.getPacks()) {
				CommitGraph cg = pack.getCommitGraph(this);
				if (cg != null) {
					return Optional.of(cg);
				}
			}
		} catch (IOException e) {
		}
		return Optional.empty();
	}

