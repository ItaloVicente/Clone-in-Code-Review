	@Test
	public void testInvalidTreeNameIsGit() {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.checkTree(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals("invalid name '.git'"
		}
	}

	@Test
	public void testInvalidTreeNameIsGitUpper() {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.setIgnoreCase(true);
			checker.checkTree(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals("invalid name '.GiT'"
		}
	}

