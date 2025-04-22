		final byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.checkTree(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals("truncated in name", e.getMessage());
		}
