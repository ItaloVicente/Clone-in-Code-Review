	public List<Attribute> getEntryAttributes() throws IOException {
		return getEntryAttributes(pathLen);
	}

	protected List<Attribute> getEntryAttributes(final int pLen)
			throws IOException {
		AttributesNode rules = getAttributesNode();
		if (rules != null) {
			int pOff = pathOffset;
			if (0 < pOff)
				pOff--;
			String p = TreeWalk.pathOf(path
			List<Attribute> attributes = rules.getAttributes(p
					FileMode.TREE.equals(mode));
			if (!attributes.isEmpty())
				return attributes;
		}
		if (parent instanceof WorkingTreeIterator)
			return ((WorkingTreeIterator) parent).getEntryAttributes(pLen);
		return Collections.emptyList();
	}

	private AttributesNode getAttributesNode() throws IOException {
		if (attributesNode instanceof PerDirectoryAttributesNode)
			attributesNode = ((PerDirectoryAttributesNode) attributesNode)
					.load();
		return attributesNode;
	}

