	public void testIntOverflow() {
		try {
			int b=tu.decodeInt(oversizeBytes);
			fail("Got " + b + " expected assertion.");
		} catch(AssertionError e) {
		}
	}
