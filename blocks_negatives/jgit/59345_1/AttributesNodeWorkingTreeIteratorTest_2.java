	private void assertAttributeNode(String pathName,
			AttributesNode attributeNode, List<Attribute> nodeAttrs) {
		if (attributeNode == null)
			assertTrue(nodeAttrs == null || nodeAttrs.isEmpty());
		else {

			Map<String, Attribute> entryAttributes = new LinkedHashMap<String, Attribute>();
			attributeNode.getAttributes(pathName, false, entryAttributes);

			if (nodeAttrs != null && !nodeAttrs.isEmpty()) {
				for (Attribute attribute : nodeAttrs) {
					assertThat(entryAttributes.values(), hasItem(attribute));
				}
			} else {
				assertTrue(
						"The entry "
								+ pathName
								+ " should not have any attributes. Instead, the following attributes are applied to this file "
								+ entryAttributes.toString(),
						entryAttributes.isEmpty());
			}
		}
