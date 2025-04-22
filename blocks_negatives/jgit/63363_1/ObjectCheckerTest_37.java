
		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.checkTag(data);
			fail("incorrectly accepted invalid tag");
		} catch (CorruptObjectException e) {
			assertEquals("no tag header", e.getMessage());
		}
