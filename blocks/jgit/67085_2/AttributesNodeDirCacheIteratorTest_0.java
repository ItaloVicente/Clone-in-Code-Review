	private void assertAttributes(String pathName
		Attributes entryAttributes = walk.getAttributes();

		if (nodeAttrs != null && !nodeAttrs.isEmpty()) {
			for (Attribute attribute : nodeAttrs) {
				assertThat(entryAttributes.getAll()
