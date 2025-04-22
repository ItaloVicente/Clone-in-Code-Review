		final FileEntry fileEntry = (FileEntry)current();
		final File file = fileEntry.file;
		return new FileTreeIterator(this
	}

	protected AttributesQuery decorateAttributesQuery(AttributesQuery query)
			throws IOException {
		final WorkingTreeIterator parent = (WorkingTreeIterator) this.parent;
		if (parent != null)
			return parent.decorateAttributesQuery(query);

		if (gitInfoAttributes == null) {
			gitInfoAttributes = new Attributes();

			if (attributesFile.exists()) {
				final Reader reader = new FileReader(attributesFile);
				try {
					gitInfoAttributes.parse(reader);
				} finally {
					reader.close();
				}
			}
		}

		return gitInfoAttributes.isEmpty() ? query : new AttributesQuery(
				gitInfoAttributes
