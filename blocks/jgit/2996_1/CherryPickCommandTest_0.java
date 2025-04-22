	@Test
	public void testCherryPickConflictResolution() throws Exception {
		Git git = new Git(db);
		RevCommit sideCommit = prepareCherryPick(git);

		CherryPickResult result = git.cherryPick().include(sideCommit.getId())
				.call();

		assertEquals(CherryPickStatus.CONFLICTING
		assertTrue(new File(db.getDirectory()
		assertEquals("side\n\nConflicts:\n\ta"
		assertTrue(new File(db.getDirectory()
				.exists());
		assertEquals(sideCommit.getId()
		assertEquals(RepositoryState.CHERRY_PICKING

		writeTrashFile("a"
		git.add().addFilepattern("a").call();

		assertEquals(RepositoryState.CHERRY_PICKING_RESOLVED
				db.getRepositoryState());

		git.commit().setOnly("a").setMessage("resolve").call();

		assertEquals(RepositoryState.SAFE
	}

	@Test
	public void testCherryPickConflictReset() throws Exception {
		Git git = new Git(db);

		RevCommit sideCommit = prepareCherryPick(git);

		CherryPickResult result = git.cherryPick().include(sideCommit.getId())
				.call();

		assertEquals(CherryPickStatus.CONFLICTING
		assertEquals(RepositoryState.CHERRY_PICKING
		assertTrue(new File(db.getDirectory()
				.exists());

		git.reset().setMode(ResetType.MIXED).setRef("HEAD").call();

		assertEquals(RepositoryState.SAFE
		assertFalse(new File(db.getDirectory()
				.exists());
	}

