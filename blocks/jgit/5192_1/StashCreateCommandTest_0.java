
	@Test
	public void refLogIncludesCommitMessage() throws Exception {
		PersonIdent who = new PersonIdent("user"
		deleteTrashFile("file.txt");
		RevCommit stashed = git.stashCreate().setPerson(who).call();
		assertNotNull(stashed);
		assertEquals("content"
		validateStashedCommit(stashed);

		ReflogReader reader = new ReflogReader(git.getRepository()
				Constants.R_STASH);
		ReflogEntry entry = reader.getLastEntry();
		assertNotNull(entry);
		assertEquals(ObjectId.zeroId()
		assertEquals(stashed
		assertEquals(who
		assertEquals(stashed.getFullMessage()
	}
