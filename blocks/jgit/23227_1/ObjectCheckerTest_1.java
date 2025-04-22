	@Test
	public void testRejectSpaceAtEndOnWindows() {
		checker.setSafeForWindows(true);
		try {
			checkOneName("test ");
			fail("incorrectly accepted space at end");
		} catch (CorruptObjectException e) {
			assertEquals("invalid name ends with ' '"
		}
	}

	@Test
	public void testRejectDotAtEndOnWindows() {
		checker.setSafeForWindows(true);
		try {
			checkOneName("test.");
			fail("incorrectly accepted dot at end");
		} catch (CorruptObjectException e) {
			assertEquals("invalid name ends with '.'"
		}
	}

	@Test
	public void testRejectInvalidWindowsCharacters() {
		checker.setSafeForWindows(true);
		rejectName('<');
		rejectName('>');
		rejectName(':');
		rejectName('"');
		rejectName('/');
		rejectName('\\');
		rejectName('|');
		rejectName('?');
		rejectName('*');

		for (int i = 1; i <= 31; i++)
			rejectName((byte) i);
	}

	private void rejectName(char c) {
		try {
			checkOneName("te" + c + "st");
			fail("incorrectly accepted with " + c);
		} catch (CorruptObjectException e) {
			assertEquals("name contains '" + c + "'"
		}
	}

	private void rejectName(byte c) {
		String h = Integer.toHexString(c);
		try {
			checkOneName("te" + ((char) c) + "st");
			fail("incorrectly accepted with 0x" + h);
		} catch (CorruptObjectException e) {
			assertEquals("name contains byte 0x" + h
		}
	}

	private void checkOneName(String name) throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		checker.checkTree(Constants.encodeASCII(b.toString()));
	}

