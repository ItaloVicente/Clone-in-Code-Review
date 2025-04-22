	public ObjectId execute() {
		final RevCommit commit = git.getLastCommit(treeRefName);
		if (commit == null) {
			return null;
		}
		return commit.getTree().getId();
	}
