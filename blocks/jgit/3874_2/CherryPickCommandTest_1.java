	@Test
	public void testCherryPickCustomMerger() throws Exception {
		Git git = new Git(db);

		RevCommit sideCommit = prepareCherryPick(git);
		CherryPickResult result = git.cherryPick().include(sideCommit.getId())
				.mergeWith(new MockContentMerger(db)).call();
		assertEquals("custom merge - a:a(master):a(side)\n"
				read(new File(db.getWorkTree()
		assertEquals(CherryPickStatus.OK
		assertEquals(RepositoryState.SAFE
	}

