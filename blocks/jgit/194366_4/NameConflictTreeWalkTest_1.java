
	private static void assertModes(final String path
			final FileMode mode1
		assertTrue("has " + path
		assertEquals(path
		if(tw.getFileMode(0) != FileMode.MISSING) {
			assertEquals(path
		}
		if(tw.getFileMode(1) != FileMode.MISSING) {
			assertEquals(path
		}
		if(tw.getFileMode(2) != FileMode.MISSING) {
			assertEquals(path
		}
		assertEquals(mode0
		assertEquals(mode1
		assertEquals(mode2
	}
