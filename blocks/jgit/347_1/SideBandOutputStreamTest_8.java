		try {
			new SideBandOutputStream(0
			fail("Accepted 0 channel number");
		} catch (IllegalArgumentException e) {
			assertEquals("channel 0 must be in range [0
		}

		try {
			new SideBandOutputStream(256
			fail("Accepted 256 channel number");
		} catch (IllegalArgumentException e) {
			assertEquals("channel 256 must be in range [0
					.getMessage());
		}
	}

	public void testConstructor_RejectsBadBufferSize() {
		try {
			new SideBandOutputStream(CH_DATA
			fail("Accepted -1 for buffer size");
		} catch (IllegalArgumentException e) {
			assertEquals("packet size -1 must be >= 5"
		}

		try {
			new SideBandOutputStream(CH_DATA
			fail("Accepted 0 for buffer size");
		} catch (IllegalArgumentException e) {
			assertEquals("packet size 0 must be >= 5"
		}

		try {
			new SideBandOutputStream(CH_DATA
			fail("Accepted 1 for buffer size");
		} catch (IllegalArgumentException e) {
			assertEquals("packet size 1 must be >= 5"
		}

		try {
			new SideBandOutputStream(CH_DATA
			fail("Accepted " + Integer.MAX_VALUE + " for buffer size");
		} catch (IllegalArgumentException e) {
			assertEquals("packet size " + Integer.MAX_VALUE
					+ " must be <= 65520"
		}
