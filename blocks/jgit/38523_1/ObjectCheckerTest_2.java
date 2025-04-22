			assertEquals(
					"invalid name '.gi\u200Ct' contains ignorable Unicode characters"
					e.getMessage());
		}
	}

	@Test
	public void testInvalidTreeNameIsMacHFSGit2() {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = Constants.encode(b.toString());
		try {
			checker.setSafeForMacOS(true);
			checker.checkTree(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals(
					"invalid name '\u206B.git' contains ignorable Unicode characters"
					e.getMessage());
		}
	}

	@Test
	public void testInvalidTreeNameIsMacHFSGit3() {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = Constants.encode(b.toString());
		try {
			checker.setSafeForMacOS(true);
			checker.checkTree(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals(
					"invalid name '.git\uFEFF' contains ignorable Unicode characters"
					e.getMessage());
		}
	}

	private static byte[] concat(byte[] b1
		byte[] data = new byte[b1.length + b2.length];
		System.arraycopy(b1
		System.arraycopy(b2
		return data;
	}

	@Test
	public void testInvalidTreeNameIsMacHFSGitCorruptUTF8AtEnd() {
		byte[] data = concat(Constants.encode("100644 .git")
				new byte[] { (byte) 0xef });
		StringBuilder b = new StringBuilder();
		entry(b
		data = concat(data
		try {
			checker.setSafeForMacOS(true);
			checker.checkTree(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals(
					"invalid name contains byte sequence '0xef' which is not a valid UTF-8 character"
					e.getMessage());
		}
	}

	@Test
	public void testInvalidTreeNameIsMacHFSGitCorruptUTF8AtEnd2() {
		byte[] data = concat(Constants.encode("100644 .git")
				(byte) 0xe2
		StringBuilder b = new StringBuilder();
		entry(b
		data = concat(data
		try {
			checker.setSafeForMacOS(true);
			checker.checkTree(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals(
					"invalid name contains byte sequence '0xe2ab' which is not a valid UTF-8 character"
					e.getMessage());
		}
	}

	@Test
	public void testInvalidTreeNameIsNotMacHFSGit()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = Constants.encode(b.toString());
		checker.setSafeForMacOS(true);
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeNameIsNotMacHFSGit2()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = Constants.encode(b.toString());
		checker.setSafeForMacOS(true);
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeNameIsNotMacHFSGitOtherPlatform()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = Constants.encode(b.toString());
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeNameIsDotGitDot() {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.checkTree(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals("invalid name '.git.'"
		}
	}

	@Test
	public void testValidTreeNameIsDotGitDotDot()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		checker.checkTree(Constants.encodeASCII(b.toString()));
	}

	@Test
	public void testInvalidTreeNameIsDotGitSpace() {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.checkTree(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals("invalid name '.git '"
