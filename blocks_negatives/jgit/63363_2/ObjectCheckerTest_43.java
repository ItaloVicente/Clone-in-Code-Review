		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.checkTree(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals("mode starts with '0'", e.getMessage());
		}
