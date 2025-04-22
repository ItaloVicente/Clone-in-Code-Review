	public void testLongOverflow() {
		try {
			long b=tu.decodeLong(oversizeBytes);
			fail("Got " + b + " expected assertion.");
		} catch(AssertionError e) {
		}
	}
