	private boolean shouldUpdateSelection() {
		return !isDisposed() && !isViewHidden && reactOnSelection;
	}
	
	private void reactOnSelection(StructuredSelection selection) {
		if (selection.size() != 1 || !shouldUpdateSelection()) {
			return;
		}
		Object firstElement = selection.getFirstElement();
		if (firstElement instanceof RepositoryTreeNode) {
			RepositoryTreeNode repoNode = (RepositoryTreeNode) firstElement;
			if (currentRepository != repoNode.getRepository()) {
				reload(repoNode.getRepository());
