	public void testInvalidTagNoTagHeader4() {
		final StringBuilder b = new StringBuilder();

		b.append("object ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("type commit\n");
		b.append("tag foo");

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.checkTag(data);
			fail("incorrectly accepted invalid tag");
		} catch (CorruptObjectException e) {
			assertEquals("no tagger header", e.getMessage());
		}
	}

	public void testInvalidTagNoTaggerHeader1() {
