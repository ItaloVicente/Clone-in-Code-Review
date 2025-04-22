	private Boolean determineIsText() throws IOException {
		final AttributesNode node = getAttributesNode();
		if (node == null) {
			return null;
		}

		final EolTextAttributesCollector collector = new EolTextAttributesCollector();
		node.query.collect(getEntryPathString()
		return collector.getText();
	}

	private AttributesNode getAttributesNode() throws IOException {
		final WorkingTreeIterator parent = (WorkingTreeIterator)this.parent;
		if (attributesNode != null) {
			attributesNode.load(this);
			return attributesNode;
		}

		return parent != null ? parent.getAttributesNode() : null;
	}

