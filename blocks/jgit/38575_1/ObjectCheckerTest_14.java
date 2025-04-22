	@Test
	public void testInvalidTreeNameIsDotGitSomething()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = Constants.encodeASCII(b.toString());
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeNameIsDotGitSomethingSpaceSomething()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = Constants.encodeASCII(b.toString());
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeNameIsDotGitSomethingDot()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = Constants.encodeASCII(b.toString());
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeNameIsDotGitSomethingDotDot()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = Constants.encodeASCII(b.toString());
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeNameIsDotGitDotSpace() {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.checkTree(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals("invalid name '.git. '"
		}
	}

	@Test
	public void testInvalidTreeNameIsDotGitSpaceDot() {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.checkTree(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals("invalid name '.git . '"
		}
	}

	@Test
	public void testInvalidTreeNameIsGITTilde1() {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.checkTree(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals("invalid name 'GIT~1'"
		}
	}

	@Test
	public void testInvalidTreeNameIsGiTTilde1() {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.checkTree(data);
			fail("incorrectly accepted an invalid tree");
		} catch (CorruptObjectException e) {
			assertEquals("invalid name 'GiT~1'"
		}
	}

	@Test
	public void testValidTreeNameIsGitTilde11() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = Constants.encodeASCII(b.toString());
		checker.checkTree(data);
	}

