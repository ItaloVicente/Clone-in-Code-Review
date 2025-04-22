		AttributesNode load(ObjectReader reader) throws IOException {
			AttributesNode r = new AttributesNode();
			ObjectId id = ObjectId.fromRaw(raw, idOffset);
			ObjectLoader loader = reader.open(id);
			if (loader != null) {
				InputStream in = loader.openStream();
				try {
					r.parse(in);
				} finally {
					in.close();
				}
			}
			return r.getRules().isEmpty() ? null : r;
