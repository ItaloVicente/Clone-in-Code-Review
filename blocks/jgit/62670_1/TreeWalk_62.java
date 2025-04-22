
	public Attributes getAttributes() {
		if (attrs != null)
			return attrs;

		if (attributesNodeProvider == null) {
			throw new IllegalStateException(
		}

		WorkingTreeIterator workingTreeIterator = getTree(WorkingTreeIterator.class);
		DirCacheIterator dirCacheIterator = getTree(DirCacheIterator.class);
		CanonicalTreeParser other = getTree(CanonicalTreeParser.class);

		if (workingTreeIterator == null && dirCacheIterator == null
				&& other == null) {
			return new Attributes();
		}

		String path = currentHead.getEntryPathString();
		final boolean isDir = FileMode.TREE.equals(currentHead.mode);
		Attributes attributes = new Attributes();
		try {
			AttributesNode globalNodeAttr = attributesNodeProvider
					.getGlobalAttributesNode();
			AttributesNode infoNodeAttr = attributesNodeProvider
					.getInfoAttributesNode();

			if (infoNodeAttr != null) {
				infoNodeAttr.getAttributes(path
			}

			getPerDirectoryEntryAttributes(path
					workingTreeIterator

			if (globalNodeAttr != null) {
				globalNodeAttr.getAttributes(path
			}
		} catch (IOException e) {
			throw new JGitInternalException("Error while parsing attributes"
		}
		for (Attribute a : attributes.getAll()) {
			if (a.getState() == State.UNSPECIFIED)
				attributes.remove(a.getKey());
		}
		return attributes;
	}

	private void getPerDirectoryEntryAttributes(String path
			OperationType opType
			DirCacheIterator dirCacheIterator
			Attributes attributes)
			throws IOException {
		if (workingTreeIterator != null || dirCacheIterator != null
				|| other != null) {
			AttributesNode currentAttributesNode = getCurrentAttributesNode(
					opType
			if (currentAttributesNode != null) {
				currentAttributesNode.getAttributes(path
			}
			getPerDirectoryEntryAttributes(path
					getParent(workingTreeIterator
					getParent(dirCacheIterator
					getParent(other
		}
	}

	private static <T extends AbstractTreeIterator> T getParent(T current
			Class<T> type) {
		if (current != null) {
			AbstractTreeIterator parent = current.parent;
			if (type.isInstance(parent)) {
				return type.cast(parent);
			}
		}
		return null;
	}

	private <T extends AbstractTreeIterator> T getTree(Class<T> type) {
		for (int i = 0; i < trees.length; i++) {
			AbstractTreeIterator tree = trees[i];
			if (type.isInstance(tree)) {
				return type.cast(tree);
			}
		}
		return null;
	}

	private AttributesNode getCurrentAttributesNode(OperationType opType
			@Nullable WorkingTreeIterator workingTreeIterator
			@Nullable DirCacheIterator dirCacheIterator
			@Nullable CanonicalTreeParser other)
					throws IOException {
		AttributesNode attributesNode = null;
		switch (opType) {
		case CHECKIN_OP:
			if (workingTreeIterator != null) {
				attributesNode = workingTreeIterator.getEntryAttributesNode();
			}
			if (attributesNode == null && dirCacheIterator != null) {
				attributesNode = getAttributesNode(dirCacheIterator
						.getEntryAttributesNode(getObjectReader())
						attributesNode);
			}
			if (attributesNode == null && other != null) {
				attributesNode = getAttributesNode(
						other.getEntryAttributesNode(getObjectReader())
						attributesNode);
			}
			break;
		case CHECKOUT_OP:
			if (other != null) {
				attributesNode = other
						.getEntryAttributesNode(getObjectReader());
			}
			if (dirCacheIterator != null) {
				attributesNode = getAttributesNode(dirCacheIterator
						.getEntryAttributesNode(getObjectReader())
						attributesNode);
			}
			if (attributesNode == null && workingTreeIterator != null) {
				attributesNode = getAttributesNode(
						workingTreeIterator.getEntryAttributesNode()
						attributesNode);
			}
			break;
		default:
			throw new IllegalStateException(
							+ OperationType.CHECKIN_OP + "
	public String getFilterCommand(String filterCommandType)
			throws IOException {
		Attributes attributes = getAttributes();

		Attribute f = attributes.get(Constants.ATTR_FILTER);
		if (f == null) {
			return null;
		}
		String filterValue = f.getValue();
		if (filterValue == null) {
			return null;
		}

		String filterCommand = getFilterCommandDefinition(filterValue
				filterCommandType);
		if (filterCommand == null) {
			return null;
		}
		return filterCommand.replaceAll("%f"
				QuotedString.BOURNE.quote((getPathString())));
	}

	private String getFilterCommandDefinition(String filterDriverName
			String filterCommandType) {
		String filterCommand = filterCommandsByNameDotType.get(key);
		if (filterCommand != null)
			return filterCommand;
		filterCommand = config.getString(Constants.ATTR_FILTER
				filterDriverName
		if (filterCommand != null)
			filterCommandsByNameDotType.put(key
		return filterCommand;
	}
