
	public void selectRevealRef(Repository repository, String refName) {
		RepositoryTreeNode branchesNode = getRepositoryChildNode(repository,
				RepositoryTreeNodeType.BRANCHES);
		ITreeContentProvider cp = (ITreeContentProvider) getCommonViewer()
				.getContentProvider();

		RepositoryTreeNode refNode = findRefNodeRecursive(cp, branchesNode,
				refName);
		if (refNode != null) {
			selectReveal(new StructuredSelection(refNode));
		}
	}

	private RepositoryTreeNode findRefNodeRecursive(ITreeContentProvider cp,
			RepositoryTreeNode currentNode, String refName) {
		for (Object child : cp.getChildren(currentNode)) {
			RepositoryTreeNode childNode = (RepositoryTreeNode) child;
			if (childNode.getType() == RepositoryTreeNodeType.REF) {
				Ref ref = (Ref) childNode.getObject();
				if (ref.getName().equals(refName)) {
					return childNode;
				}
			} else {
				RepositoryTreeNode result = findRefNodeRecursive(cp, childNode,
						refName);
				if (result != null) {
					return result;
				}
			}
		}
		return null;
	}
