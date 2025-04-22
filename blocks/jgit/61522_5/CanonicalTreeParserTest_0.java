
	@Test
	public void testFindAttributesWhenFirst() throws CorruptObjectException {
		TreeFormatter tree = new TreeFormatter();
		tree.append(".gitattributes"
		ctp.reset(tree.toByteArray());

		assertTrue(ctp.findFile(".gitattributes"));
		assertEquals(REGULAR_FILE.getBits()
		assertEquals(".gitattributes"
		assertEquals(hash_a
	}

	@Test
	public void testFindAttributesWhenSecond() throws CorruptObjectException {
		TreeFormatter tree = new TreeFormatter();
		tree.append(".config"
		tree.append(".gitattributes"
		ctp.reset(tree.toByteArray());

		assertTrue(ctp.findFile(".gitattributes"));
		assertEquals(REGULAR_FILE.getBits()
		assertEquals(".gitattributes"
		assertEquals(hash_foo
	}

	@Test
	public void testFindAttributesWhenMissing() throws CorruptObjectException {
		TreeFormatter tree = new TreeFormatter();
		tree.append("src"
		tree.append("zoo"
		ctp.reset(tree.toByteArray());

		assertFalse(ctp.findFile(".gitattributes"));
		assertEquals(11
		assertEquals("src"
	}
