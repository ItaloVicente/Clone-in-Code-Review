	@Test
	public void testInvalidTreeNameIsMixedCaseGitMacOS() {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.setSafeForMacOS(true);
			checker.checkTree(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals("invalid name '.GiT'"
		}
	}

