	public void testMarkSupported() {
		@SuppressWarnings("resource" /* java 7 */)
		final UnionInputStream u = new UnionInputStream();
		assertFalse(u.markSupported());
		u.add(new ByteArrayInputStream(new byte[] { 1, 0, 2 }));
		assertFalse(u.markSupported());
