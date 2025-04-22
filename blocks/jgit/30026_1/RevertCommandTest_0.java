	@Test
	public void testRevertMultiple() throws IOException
			GitAPIException {
		Git git = new Git(db);

		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("add first").call();

		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		RevCommit secondCommit = git.commit().setMessage("add second").call();

		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		RevCommit thirdCommit = git.commit().setMessage("add third").call();

		git.revert().include(thirdCommit).include(secondCommit).call();

		checkFile(new File(db.getWorkTree()
		Iterator<RevCommit> history = git.log().call().iterator();
		RevCommit revertCommit = history.next();
		String expectedMessage = "Revert \"add second\"\n\n"
				+ "This reverts commit "
				+ secondCommit.getId().getName() + ".\n";
		assertEquals(expectedMessage
		revertCommit = history.next();
		expectedMessage = "Revert \"add third\"\n\n"
				+ "This reverts commit " + thirdCommit.getId().getName()
				+ ".\n";
		assertEquals(expectedMessage
		assertEquals("add third"
		assertEquals("add second"
		assertEquals("add first"
		assertFalse(history.hasNext());

		ReflogReader reader = db.getReflogReader(Constants.HEAD);
		assertTrue(reader.getLastEntry().getComment()
				.startsWith("revert: Revert \""));
		reader = db.getReflogReader(db.getBranch());
		assertTrue(reader.getLastEntry().getComment()
				.startsWith("revert: Revert \""));

	}

