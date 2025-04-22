	@Test
	public void testInvalidTreeDuplicateNames1_Tree()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		entry(b
		byte[] data = encodeASCII(b.toString());
		assertCorrupt("duplicate entry names"
		assertSkipListAccepts(OBJ_TREE
		checker.setIgnore(DUPLICATE_ENTRIES
		checker.checkTree(data);
	}

