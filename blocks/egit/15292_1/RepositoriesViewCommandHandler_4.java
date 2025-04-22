	private boolean repositoryHasHead(T treeNode) {
		Repository repo = treeNode.getRepository();
		try {
			Ref ref = repo.getRef(Constants.HEAD);
			return ref != null && ref.getObjectId() != null;
		} catch (IOException e) {
			return false;
