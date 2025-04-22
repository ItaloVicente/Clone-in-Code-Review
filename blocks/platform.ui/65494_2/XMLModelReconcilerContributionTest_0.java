	protected boolean serializedStateHasChanges(Object state) {
		assertTrue(state instanceof org.w3c.dom.Document);
		org.w3c.dom.Document doc = (org.w3c.dom.Document) state;
		assertTrue(doc.hasChildNodes() && doc.getChildNodes().getLength() == 1
				&& "changes".equals(doc.getFirstChild().getNodeName()));
		return doc.getFirstChild().hasChildNodes();
	}
