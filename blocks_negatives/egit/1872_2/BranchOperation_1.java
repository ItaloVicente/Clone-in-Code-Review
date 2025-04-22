	private void mapObjects() throws TeamException {
		try {
			oldTree = oldCommit.getTree();
			index = repository.getIndex();
			newTree = newCommit.getTree();
		} catch (IOException e) {
			throw new TeamException(CoreText.BranchOperation_mappingTrees, e);
