	public List<Attribute> getEntryAttributes() throws IOException {
		Collection<Attribute> attributes = getEntryAttributes(pathLen).values();
		return attributes.isEmpty() ? Collections.<Attribute> emptyList()
				: new ArrayList<Attribute>(attributes);
	}

	protected Map<String
			throws IOException {
		int pOff = pathOffset;
		if (0 < pOff)
			pOff--;
		final String p = TreeWalk.pathOf(path
		Map<String
		final boolean isDir = FileMode.TREE.equals(mode);

		final AttributesNode infoAttributes = getInfoAttributesNode();
		if (infoAttributes != null) {
			infoAttributes.getAttributes(p
		}

		getPerDirectoryEntryAttributes(pLen

		return attributes;
	}

	protected void getPerDirectoryEntryAttributes(final int pLen
			Map<String
		int pOff = pathOffset;
		if (0 < pOff)
			pOff--;
		final String p = TreeWalk.pathOf(path
		final boolean isDir = FileMode.TREE.equals(mode);

		final AttributesNode currentRules = getAttributesNode();
		if (currentRules != null)
			currentRules.getAttributes(p

		if (parent instanceof WorkingTreeIterator) {
			((WorkingTreeIterator) parent).getPerDirectoryEntryAttributes(pLen
					attributes);
		}
	}

	private AttributesNode getAttributesNode() throws IOException {
		if (attributesNode instanceof PerDirectoryAttributesNode)
			attributesNode = ((PerDirectoryAttributesNode) attributesNode)
					.load();
		return attributesNode;
	}

	private AttributesNode getInfoAttributesNode() throws IOException {
		if (infoAttributeNode instanceof InfoAttributesNode)
			infoAttributeNode = ((InfoAttributesNode) infoAttributeNode).load();
		return infoAttributeNode;
	}

