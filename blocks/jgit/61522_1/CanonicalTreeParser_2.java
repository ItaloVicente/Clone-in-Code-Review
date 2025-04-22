
	private AttributesNode load(ObjectReader reader) throws IOException {
		AttributesNode r = new AttributesNode();
		ObjectId id = ObjectId.fromRaw(idBuffer()
		try (InputStream in = reader.open(id
			r.parse(in);
		}
		return r.getRules().isEmpty() ? noAttributes() : r;
	}

	private static AttributesNode noAttributes() {
		return new AttributesNode(Collections.<AttributesRule> emptyList());
	}
