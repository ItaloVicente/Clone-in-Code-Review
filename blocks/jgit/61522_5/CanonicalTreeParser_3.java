	private AttributesNode findAttributes(ObjectReader reader)
			throws IOException {
		CanonicalTreeParser itr = new CanonicalTreeParser();
		itr.reset(raw);
		if (itr.findFile(ATTRS)) {
			return loadAttributes(reader
