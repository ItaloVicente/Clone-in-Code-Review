
	private static class AttributesNode {
		final Entry entry;

		AttributesQuery query;

		AttributesNode(Entry entry) {
			this.entry = entry;
		}

		AttributesQuery load(WorkingTreeIterator iterator) throws IOException {
			if (query != null) {
				return query;
			}

			final Attributes attributes = new Attributes();
			final InputStream in = entry.openInputStream();
			try {
				attributes.parse(new InputStreamReader(in));
			} finally {
				in.close();
			}

			final WorkingTreeIterator parentIterator = (WorkingTreeIterator) iterator.parent;
			final AttributesNode parentNode = parentIterator != null ? parentIterator
					.getAttributesNode() : null;
			final AttributesQuery parentQuery = parentNode != null ? parentNode.query
					: null;

			final String pathString = iterator.getEntryPathString();
			final String basePath = pathString.substring(0
					- RawParseUtils.pathTail(pathString).length());
			query = iterator.decorateAttributesQuery(new AttributesQuery(
					attributes
			return query;
		}
	}

	private static class EolTextAttributesCollector implements
			AttributesCollector {
		private static final String TEXT = "text";

		private static final String EOL = "eol";

		private Boolean text;

		public Boolean getText() {
			return text;
		}

		public boolean collect(Attribute attribute) {
			final String key = attribute.getKey();
			if (!TEXT.equals(key) && !EOL.equals(key))
				return true;

			final AttributeValue value = attribute.getValue();
			if (value == AttributeValue.UNSET)
				text = Boolean.FALSE;
			else if (value != null)
				text = Boolean.TRUE;

			return EOL.equals(key);
		}
	}
