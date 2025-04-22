	@Test
	public void testCommitCorruptAuthor() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		b.append("tree be9bfa841874ccc9f2ef7c48d0c76226f89b7189\n");
		b.append("author b <b@c> <b@c> 0 +0000\n");
		b.append("committer <> 0 +0000\n");

		byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.checkCommit(data);
			fail("Did not catch corrupt object");
		} catch (CorruptObjectException e) {
			assertEquals("invalid author"
		}
		checker.setAllowInvalidPersonIdent(true);
		checker.checkCommit(data);
	}

	@Test
	public void testCommitCorruptCommitter() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		b.append("tree be9bfa841874ccc9f2ef7c48d0c76226f89b7189\n");
		b.append("author <> 0 +0000\n");
		b.append("committer b <b@c> <b@c> 0 +0000\n");

		byte[] data = Constants.encodeASCII(b.toString());
		try {
			checker.checkCommit(data);
			fail("Did not catch corrupt object");
		} catch (CorruptObjectException e) {
			assertEquals("invalid committer"
		}
		checker.setAllowInvalidPersonIdent(true);
		checker.checkCommit(data);
	}

