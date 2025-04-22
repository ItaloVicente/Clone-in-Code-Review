	private static <T extends AbstractTreeIterator> T getRoot(T current
			Class<T> type) {
		if (current != null) {
			AbstractTreeIterator root = current.root;
			if (type.isInstance(root)) {
				return type.cast(root);
			}
		}
		return null;
	}

