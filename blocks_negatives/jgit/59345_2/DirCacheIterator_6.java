	/**
	 * Retrieves the {@link AttributesNode} for the current entry.
	 *
	 * @param reader
	 *            {@link ObjectReader} used to parse the .gitattributes entry.
	 * @return {@link AttributesNode} for the current entry.
	 * @throws IOException
	 * @since 3.7
	 */
	public AttributesNode getEntryAttributesNode(ObjectReader reader)
			throws IOException {
		if (attributesNode instanceof LazyLoadingAttributesNode)
			attributesNode = ((LazyLoadingAttributesNode) attributesNode)
					.load(reader);
		return attributesNode;
	}

	/**
	 * {@link AttributesNode} implementation that provides lazy loading
	 * facilities.
	 */
	private static class LazyLoadingAttributesNode extends AttributesNode {
		final ObjectId objectId;

		LazyLoadingAttributesNode(ObjectId objectId) {
			super(Collections.<AttributesRule> emptyList());
			this.objectId = objectId;

		}

		AttributesNode load(ObjectReader reader) throws IOException {
			AttributesNode r = new AttributesNode();
			ObjectLoader loader = reader.open(objectId);
			if (loader != null) {
				InputStream in = loader.openStream();
				try {
					r.parse(in);
				} finally {
					in.close();
				}
			}
			return r.getRules().isEmpty() ? null : r;
		}
	}

