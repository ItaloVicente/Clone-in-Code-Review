	public void testByteOverflow() {
		try {
			byte b=tu.decodeByte(oversizeBytes);
			fail("Got " + b + " expected assertion.");
		} catch(AssertionError e) {
		}
	}
