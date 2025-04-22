
	public Map<String
		if (attrs != null)
			return attrs;

		if (attributesNodeProvider == null) {
			throw new IllegalStateException(
		}

		WorkingTreeIterator workingTreeIterator = getTree(WorkingTreeIterator.class);
		if (workingTreeIterator != null
				&& workingTreeIterator.matches != currentHead) {
			workingTreeIterator = null;
		}

		DirCacheIterator dirCacheIterator = getTree(DirCacheIterator.class);
		if (dirCacheIterator != null
				&& ((AbstractTreeIterator) dirCacheIterator).matches != currentHead) {
			dirCacheIterator = null;
		}

		if (workingTreeIterator == null
				&& dirCacheIterator == null) {
			return Collections.<String
		}

		String path = currentHead.getEntryPathString();
		final boolean isDir = FileMode.TREE.equals(currentHead.mode);
		Map<String
		try {
			AttributesNode infoNodeAttr = attributesNodeProvider
					.getInfoAttributesNode();
			if (infoNodeAttr != null) {
				infoNodeAttr.getAttributes(path
			}


			getPerDirectoryEntryAttributes(path
					workingTreeIterator
					attributes);

			AttributesNode globalNodeAttr = attributesNodeProvider
					.getGlobalAttributesNode();
			if (globalNodeAttr != null) {
				globalNodeAttr.getAttributes(path
			}
		} catch (IOException e) {
			throw new JGitInternalException("Error while parsing attributes"
		}
		return attributes;
	}

	private void getPerDirectoryEntryAttributes(String path
			OperationType opType
			DirCacheIterator dirCacheIterator
			throws IOException {
		if (workingTreeIterator != null || dirCacheIterator != null) {
			AttributesNode currentAttributesNode = getCurrentAttributesNode(
					opType
			if (currentAttributesNode != null) {
				currentAttributesNode.getAttributes(path
			}
			getPerDirectoryEntryAttributes(path
					getParent(workingTreeIterator
					getParent(dirCacheIterator
					attributes);
		}
	}

	private <T extends AbstractTreeIterator> T getParent(T current
			Class<T> type) {
		if (current != null) {
			AbstractTreeIterator parent = current.parent;
			if (type.isInstance(parent)) {
				return type.cast(parent);
			}
		}
		return null;
	}

	private <T> T getTree(Class<T> type) {
		for (int i = 0; i < trees.length; i++) {
			AbstractTreeIterator tree = trees[i];
			if (type.isInstance(tree)) {
				return type.cast(tree);
			}
		}
		return null;
	}

	private AttributesNode getCurrentAttributesNode(OperationType opType
			WorkingTreeIterator workingTreeIterator
			DirCacheIterator dirCacheIterator) throws IOException {
		AttributesNode attributesNode = null;
		switch (opType) {
		case CHECKIN_OP:
			if (workingTreeIterator != null) {
				attributesNode = workingTreeIterator.getEntryAttributesNode();
			}
			if (attributesNode == null && dirCacheIterator != null) {
				attributesNode = dirCacheIterator
						.getEntryAttributesNode(getObjectReader());
			}
			break;
		case CHECKOUT_OP:
			if (dirCacheIterator != null) {
				attributesNode = dirCacheIterator
						.getEntryAttributesNode(getObjectReader());
			}
			if (attributesNode == null && workingTreeIterator != null) {
				attributesNode = workingTreeIterator.getEntryAttributesNode();
			}
			break;
		default:
			throw new IllegalStateException(
							+ OperationType.CHECKIN_OP + "
