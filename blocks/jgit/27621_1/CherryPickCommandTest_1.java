	@Test
	public void testCherryPickConflictResolutionNoCOmmit() throws Exception {
		Git git = new Git(db);
		RevCommit sideCommit = prepareCherryPick(git);

		CherryPickResult result = git.cherryPick().include(sideCommit.getId())
				.setNoCommit(true).call();

		assertEquals(CherryPickStatus.CONFLICTING
		assertTrue(db.readDirCache().hasUnmergedPaths());
		String expected = "<<<<<<< master\na(master)\n=======\na(side)\n>>>>>>> 527460a side\n";
		assertEquals(expected
		assertTrue(new File(db.getDirectory()
		assertEquals("side\n\nConflicts:\n\ta\n"
		assertFalse(new File(db.getDirectory()
				.exists());
		assertEquals(RepositoryState.CHERRY_PICKING

		writeTrashFile("a"
		git.add().addFilepattern("a").call();

		assertEquals(RepositoryState.SAFE

		git.commit().setOnly("a").setMessage("resolve").call();

		assertEquals(RepositoryState.SAFE
	}

