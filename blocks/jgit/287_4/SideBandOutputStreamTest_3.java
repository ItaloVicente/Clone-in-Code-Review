	}

	public void testConstructor_RejectsBadChannel() {
		try {
			new SideBandOutputStream(-1
			fail("Accepted -1 channel number");
		} catch (IllegalArgumentException e) {
			assertEquals("channel -1 must be in range [0
		}
