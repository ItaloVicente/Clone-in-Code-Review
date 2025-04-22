	private void assertAttributesNode(String pathName,
			AttributesNode attributesNode, List<Attribute> nodeAttrs)
					throws IOException {
		if (attributesNode == null)
			assertTrue(nodeAttrs == null || nodeAttrs.isEmpty());
		else {

			Attributes entryAttributes = new Attributes();
			new AttributesHandler(walk).mergeAttributes(attributesNode,
					pathName,
					false,
					entryAttributes);

			if (nodeAttrs != null && !nodeAttrs.isEmpty()) {
				for (Attribute attribute : nodeAttrs) {
					assertThat(entryAttributes.getAll(),
							hasItem(attribute));
				}
			} else {
				assertTrue(
						"The entry "
								+ pathName
								+ " should not have any attributes. Instead, the following attributes are applied to this file "
								+ entryAttributes.toString(),
						entryAttributes.isEmpty());
