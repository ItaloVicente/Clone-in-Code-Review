	@Test
	public void testRejectNulInPathSegment() {
		try {
			checker.checkPathSegment(Constants.encodeASCII("a\u0000b")
			fail("incorrectly accepted NUL in middle of name");
		} catch (CorruptObjectException e) {
			assertEquals("name contains byte 0x00"
		}
	}

