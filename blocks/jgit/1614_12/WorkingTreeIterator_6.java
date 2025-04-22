	public List<Attribute> getEntryAttributes() throws IOException {
		return new ArrayList<Attribute>(getEntryAttributes(pathLen
				.values());
	}

	protected Map<String
			boolean incluseInfoAttributes) throws IOException {
		int pOff = pathOffset;
		if (0 < pOff)
			pOff--;
		final String p = TreeWalk.pathOf(path
		Map<String
		final boolean isDir = FileMode.TREE.equals(mode);

		final AttributesNode currentRules = getAttributesNode();
		if (currentRules != null)
			attributes = currentRules.getAttributes(p

		if (parent instanceof WorkingTreeIterator) {
			final Map<String
					.getEntryAttributes(pLen
			if (attributes == null)
				attributes = parentAttributes;
			else if (parentAttributes != null)
				attributes = mergeAttributes(parentAttributes
		}

		if (incluseInfoAttributes) {
			final AttributesNode infoAttributes = getInfoAttributesNode();
			if (infoAttributes != null) {
				if (attributes == null)
					attributes = infoAttributes.getAttributes(p
				else
					mergeAttributes(attributes
							infoAttributes.getAttributes(p

			}
		}
		return attributes != null ? attributes : Collections
				.<String

	}

	private Map<String
			Map<String
			Map<String
		for (java.util.Map.Entry<String
				.entrySet()) {
			final String attrName = overridingEntry.getKey();
			final Attribute attr = overridingEntry.getValue();
			baseAttributes.put(attrName
		}
		return baseAttributes;
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

