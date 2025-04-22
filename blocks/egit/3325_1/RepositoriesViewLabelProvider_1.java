	private RevCommit getLatestCommit(RepositoryTreeNode node) {
		RevWalk walk = new RevWalk(node.getRepository());
		try {
			return walk.parseCommit(((Ref) node.getObject()).getObjectId());
		} catch (IOException ignored) {
			return null;
		} finally {
			walk.release();
		}
	}

