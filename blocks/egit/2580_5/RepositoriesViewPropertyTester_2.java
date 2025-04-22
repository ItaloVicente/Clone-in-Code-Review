
	private boolean containsHead(RepositoryTreeNode node) {
		try {
			return node.getRepository().resolve(Constants.HEAD) != null;
		} catch (IOException e) {
			return false;
		}
	}

