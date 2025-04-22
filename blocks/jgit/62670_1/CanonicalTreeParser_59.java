		nextPtr = ptr + OBJECT_ID_LENGTH;
	}

	public AttributesNode getEntryAttributesNode(ObjectReader reader)
			throws IOException {
		if (attributesNode == null) {
			attributesNode = findAttributes(reader);
		}
		return attributesNode.getRules().isEmpty() ? null : attributesNode;
	}

	private AttributesNode findAttributes(ObjectReader reader)
			throws IOException {
		CanonicalTreeParser itr = new CanonicalTreeParser();
		itr.reset(raw);
		if (itr.findFile(ATTRS)) {
			return loadAttributes(reader
		}
		return noAttributes();
	}

	private static AttributesNode loadAttributes(ObjectReader reader
			AnyObjectId id) throws IOException {
		AttributesNode r = new AttributesNode();
		try (InputStream in = reader.open(id
			r.parse(in);
		}
		return r.getRules().isEmpty() ? noAttributes() : r;
	}

	private static AttributesNode noAttributes() {
		return new AttributesNode(Collections.<AttributesRule> emptyList());
