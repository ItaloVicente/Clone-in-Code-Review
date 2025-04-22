	@Test
	public void testCherryPickConflictMarkers() throws Exception {
		Git git = new Git(db);
		RevCommit sideCommit = prepareCherryPick(git);

		CherryPickResult result = git.cherryPick().include(sideCommit.getId())
				.call();
		assertEquals(CherryPickStatus.CONFLICTING

		String expected = "<<<<<<< master\na(master)\n=======\na(side)\n>>>>>>> 527460a side\n";
		checkFile(new File(db.getWorkTree()
	}

	@Test
	public void testCherryPickOurCommitName() throws Exception {
		Git git = new Git(db);
		RevCommit sideCommit = prepareCherryPick(git);

		CherryPickResult result = git.cherryPick().include(sideCommit.getId())
				.setOurCommitName("custom name").call();
		assertEquals(CherryPickStatus.CONFLICTING

		String expected = "<<<<<<< custom name\na(master)\n=======\na(side)\n>>>>>>> 527460a side\n";
		checkFile(new File(db.getWorkTree()
	}

