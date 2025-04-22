
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

