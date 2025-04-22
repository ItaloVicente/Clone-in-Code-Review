
	protected Ref getRef(Object selected) {
		if (selected instanceof RepositoryTreeNode<?>) {
			RepositoryTreeNode node = (RepositoryTreeNode) selected;
			if (node.getType() == RepositoryTreeNodeType.REF)
				return ((RefNode) node).getObject();
		}

		return null;
	}

	protected Shell getShell(ExecutionEvent event) {
		return HandlerUtil.getActiveShell(event);
	}

