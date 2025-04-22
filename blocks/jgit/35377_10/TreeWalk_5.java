
	public static enum OperationType {
		CHECKOUT_OP
		CHECKIN_OP
	}

	public Set<Attribute> getAttributes(OperationType operationType) {
		Set<Attribute> cachedAttributes = getCachedAttribute(operationType);
		if (cachedAttributes != null)
			return cachedAttributes;

		if (attributeNodeProvider == null) {
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
			return Collections.<Attribute> emptySet();
		}

		String path = currentHead.getEntryPathString();
		final boolean isDir = FileMode.TREE.equals(currentHead.mode);
		Map<String
		try {
			AttributesNode infoNodeAttr = attributeNodeProvider
					.getInfoAttributesNode();
			if (infoNodeAttr != null) {
				infoNodeAttr.getAttributes(path
			}

			getPerDirectoryEntryAttributes(path
					workingTreeIterator
					attributes);

			AttributesNode globalNodeAttr = attributeNodeProvider
					.getGlobalAttributesNode();
			if (globalNodeAttr != null) {
				globalNodeAttr.getAttributes(path
			}
		} catch (IOException e) {
			throw new JGitInternalException("Error while parsing attributes"
		}
		final Set<Attribute> result;
		if (attributes.isEmpty()) {
			result = Collections.<Attribute> emptySet();
		} else {
			result = new HashSet<Attribute>(attributes.values());
		}

		setCachedAttribute(operationType

		return result;
	}

	private Set<Attribute> getCachedAttribute(OperationType type) {
		switch (type) {
		case CHECKIN_OP:
			return checkinAttrs;
		case CHECKOUT_OP:
			return checkoutAttrs;
		default:
					+ type);
		}
	}

	private void setCachedAttribute(OperationType type
		switch (type) {
		case CHECKIN_OP:
			checkinAttrs = attrs;
			break;
		case CHECKOUT_OP:
			checkoutAttrs = attrs;
			break;
		default:
					+ type);
		}
	}

	private void resetCachedAttributes() {
		checkoutAttrs = null;
		checkinAttrs = null;
	}

	private void getPerDirectoryEntryAttributes(String path
			OperationType opType
			DirCacheIterator dirCacheIterator
			throws IOException {
		if (workingTreeIterator != null || dirCacheIterator != null) {
			AttributesNode currentAttributeNode = getCurrentAttributesNode(
					opType
			if (currentAttributeNode != null) {
				currentAttributeNode.getAttributes(path
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
