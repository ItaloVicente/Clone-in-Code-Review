	@Test
	public void testCommentCharDefault() throws Exception {
		CommitConfig cfg = parse("");
		assertEquals('#'
		assertFalse(cfg.isAutoCommentChar());
	}

	@Test
	public void testCommentCharAuto() throws Exception {
		CommitConfig cfg = parse("[core]\n\tcommentChar = auto\n");
		assertEquals('#'
		assertTrue(cfg.isAutoCommentChar());
	}

	@Test
	public void testCommentCharEmpty() throws Exception {
		CommitConfig cfg = parse("[core]\n\tcommentChar =\n");
		assertEquals('#'
	}

	@Test
	public void testCommentCharInvalid() throws Exception {
		CommitConfig cfg = parse("[core]\n\tcommentChar = \" \"\n");
		assertEquals('#'
	}

	@Test
	public void testCommentCharNonAscii() throws Exception {
		CommitConfig cfg = parse("[core]\n\tcommentChar = ö\n");
		assertEquals('#'
	}

	@Test
	public void testCommentChar() throws Exception {
		CommitConfig cfg = parse("[core]\n\tcommentChar = _\n");
		assertEquals('_'
	}

	@Test
	public void testDetermineCommentChar() throws Exception {
		String text = "A commit message\n\nBody\n";
		assertEquals('#'
	}

	@Test
	public void testDetermineCommentChar2() throws Exception {
		String text = "A commit message\n\nBody\n\n# Conflicts:\n#\tfoo.txt\n";
		char ch = CommitConfig.determineCommentChar(text);
		assertNotEquals('#'
		assertTrue(ch > ' ' && ch < 127);
	}

	@Test
	public void testDetermineCommentChar3() throws Exception {
		String text = "A commit message\n\n;Body\n\n# Conflicts:\n#\tfoo.txt\n";
		char ch = CommitConfig.determineCommentChar(text);
		assertNotEquals('#'
		assertNotEquals(';'
		assertTrue(ch > ' ' && ch < 127);
	}

	@Test
	public void testDetermineCommentChar4() throws Exception {
		String text = "A commit message\n\nBody\n\n  # Conflicts:\n\t #\tfoo.txt\n";
		char ch = CommitConfig.determineCommentChar(text);
		assertNotEquals('#'
		assertTrue(ch > ' ' && ch < 127);
	}

	@Test
	public void testDetermineCommentChar5() throws Exception {
		String text = "A commit message\n\nBody\n\n#a\n;b\n@c\n!d\n$\n%\n^\n&\n|\n:";
		char ch = CommitConfig.determineCommentChar(text);
		assertEquals(0
	}

