	private boolean checkRepositoryHasHead(Object element) {
		if (element instanceof RepositoryTreeNode) {
			RepositoryTreeNode<?> treeNode = (RepositoryTreeNode<?>) element;
			Repository repo = treeNode.getRepository();
			try {
				Ref ref = repo.getRef(Constants.HEAD);
				return ref != null && ref.getObjectId() != null;
			} catch (IOException e) {
				return false;
			}
		}
		return false;
	}

	protected boolean checkSelectionHasHead(Object evaluationContext,
			boolean all) {
