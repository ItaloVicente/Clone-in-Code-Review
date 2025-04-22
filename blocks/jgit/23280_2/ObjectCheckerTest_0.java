	@Test
	public void testInvalidTreeDuplicateNames5()
			throws UnsupportedEncodingException {
		StringBuilder b = new StringBuilder();
		entry(b
		entry(b
		byte[] data = b.toString().getBytes("UTF-8");
		try {
			checker.setSafeForWindows(true);
			checker.checkTree(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals("duplicate entry names"
		}
	}

	@Test
	public void testInvalidTreeDuplicateNames6()
			throws UnsupportedEncodingException {
		StringBuilder b = new StringBuilder();
		entry(b
		entry(b
		byte[] data = b.toString().getBytes("UTF-8");
		try {
			checker.setSafeForMacOS(true);
			checker.checkTree(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals("duplicate entry names"
		}
	}

	@Test
	public void testInvalidTreeDuplicateNames7()
			throws UnsupportedEncodingException {
		try {
			Class.forName("java.text.Normalizer");
		} catch (ClassNotFoundException e) {
			return;
		}

		StringBuilder b = new StringBuilder();
		entry(b
		entry(b
		byte[] data = b.toString().getBytes("UTF-8");
		try {
			checker.setSafeForMacOS(true);
			checker.checkTree(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals("duplicate entry names"
		}
	}

