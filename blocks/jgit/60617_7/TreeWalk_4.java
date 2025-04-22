	private <T extends AbstractTreeIterator> T getRootTreeNode(Class<T> type) {
		AbstractTreeIterator node = getCurrentTreeNode(type);
		while (node != null && node.parent != null) {
			node = node.parent;
		}
		if (type.isInstance(node)) {
			return type.cast(node);
		}
		return null;
	}

