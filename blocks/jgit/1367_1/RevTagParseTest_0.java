	public void testParse_PublicParseMethod() throws CorruptObjectException {
		ObjectInserter.Formatter fmt = new ObjectInserter.Formatter();
		Tag src = new Tag();
		src.setObjectId(fmt.idFor(Constants.OBJ_TREE
				Constants.OBJ_TREE);
		src.setTagger(committer);
		src.setTag("a.test");
		src.setMessage("Test tag\n\nThis is a test.\n");

		RevTag p = RevTag.parse(fmt.format(src));
		assertEquals(src.getObjectId()
		assertEquals(committer
		assertEquals("a.test"
		assertEquals("Test tag"
		assertEquals(src.getMessage()
	}

