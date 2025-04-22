	public void testParse_PublicParseMethod()
			throws UnsupportedEncodingException {
		ObjectInserter.Formatter fmt = new ObjectInserter.Formatter();
		Commit src = new Commit();
		src.setTreeId(fmt.idFor(Constants.OBJ_TREE
		src.setAuthor(author);
		src.setCommitter(committer);
		src.setMessage("Test commit\n\nThis is a test.\n");

		RevCommit p = RevCommit.parse(fmt.format(src));
		assertEquals(src.getTreeId()
		assertEquals(0
		assertEquals(author
		assertEquals(committer
		assertEquals("Test commit"
		assertEquals(src.getMessage()
	}

