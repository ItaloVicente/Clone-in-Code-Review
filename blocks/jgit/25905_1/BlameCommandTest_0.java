
	@Test
	public void testWhitespaceMerge() throws Exception {
		Git git = new Git(db);
		RevCommit base = commitFile("file.txt"
		RevCommit side = commitFile("file.txt"
				"side");

		checkoutBranch("refs/heads/master");
		git.merge().setFastForward(FastForwardMode.NO_FF).include(side).call();

		writeTrashFile("file.txt"
		RevCommit merge = git.commit().setAll(true).setMessage("merge")
				.setAmend(true)
				.call();

		BlameCommand command = new BlameCommand(db);
		command.setFilePath("file.txt")
				.setTextComparator(RawTextComparator.WS_IGNORE_ALL)
				.setStartCommit(merge.getId());
		BlameResult lines = command.call();

		assertEquals(3
		assertEquals(base
		assertEquals(base
		assertEquals(side
	}
