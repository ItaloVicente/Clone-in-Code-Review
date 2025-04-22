		if (node instanceof StashedCommitNode) {
			RepositoryCommit repositoryCommit = new RepositoryCommit(
					node.getRepository(),
					((StashedCommitNode) node).getObject());
			repositoryCommit.setStash(true);
			CommitEditor.openQuiet(repositoryCommit);
		}
