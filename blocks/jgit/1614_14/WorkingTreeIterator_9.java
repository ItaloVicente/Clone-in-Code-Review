	public AttributesNode getEntryAttributesNode() throws IOException {
		if (attributesNode instanceof PerDirectoryAttributesNode)
			attributesNode = ((PerDirectoryAttributesNode) attributesNode)
					.load();
		return attributesNode;
	}

	public AttributesNode getInfoAttributesNode() throws IOException {
		if (infoAttributeNode instanceof InfoAttributesNode)
			infoAttributeNode = ((InfoAttributesNode) infoAttributeNode).load();
		return infoAttributeNode;
	}

	public AttributesNode getGlobalAttributesNode() throws IOException {
		if (globalAttributeNode instanceof GlobalAttributesNode)
			globalAttributeNode = ((GlobalAttributesNode) globalAttributeNode)
					.load();
		return globalAttributeNode;
	}

