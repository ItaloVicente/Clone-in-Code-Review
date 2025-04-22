		final StringBuilder b = new StringBuilder();

		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.checkTag(data);
			fail("incorrectly accepted invalid tag");
		} catch (CorruptObjectException e) {
			assertEquals("no object header", e.getMessage());
		}
