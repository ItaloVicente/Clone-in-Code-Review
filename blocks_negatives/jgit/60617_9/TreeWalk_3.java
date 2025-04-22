		if (current != null) {
			AbstractTreeIterator parent = current.parent;
			if (type.isInstance(parent)) {
				return type.cast(parent);
			}
		}
		return null;
	}

	private <T extends AbstractTreeIterator> T getTree(Class<T> type) {
