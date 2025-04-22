
		if (attributesNode instanceof PerDirectoryAttributesNode)
			entry = ((PerDirectoryAttributesNode) attributesNode).entry;
		else
			entry = null;
		attributesNode = new RootAttributesNode(entry
