
		if (RawParseUtils.match(path
				Constants.DOT_GIT_ATTRIBUTES.getBytes()) == ATTRIBUTESLENGTH)
			attributesNode = new LazyLoadingAttributesNode(
					ObjectId.fromRaw(idBuffer()

	}

	public AttributesNode getEntryAttributesNode(ObjectReader reader)
			throws IOException {
		if (attributesNode instanceof LazyLoadingAttributesNode)
			attributesNode = ((LazyLoadingAttributesNode) attributesNode)
					.load(reader);
		return attributesNode;
