		writeTrashFile("a", "first\nsecond\n");
		git.add().addFilepattern("a").call();
		RevCommit secondCommit = git.commit().setMessage("add second").call();

		writeTrashFile("a", "first\nsecond\nthird\n");
		git.add().addFilepattern("a").call();
		RevCommit thirdCommit = git.commit().setMessage("add third").call();

		git.revert().include(thirdCommit).include(secondCommit).call();

		assertEquals(RepositoryState.SAFE, db.getRepositoryState());

		checkFile(new File(db.getWorkTree(), "a"), "first\n");
		Iterator<RevCommit> history = git.log().call().iterator();
		RevCommit revertCommit = history.next();
		String expectedMessage = "Revert \"add second\"\n\n"
				+ "This reverts commit "
				+ secondCommit.getId().getName() + ".\n";
		assertEquals(expectedMessage, revertCommit.getFullMessage());
		revertCommit = history.next();
		expectedMessage = "Revert \"add third\"\n\n"
				+ "This reverts commit " + thirdCommit.getId().getName()
				+ ".\n";
		assertEquals(expectedMessage, revertCommit.getFullMessage());
		assertEquals("add third", history.next().getFullMessage());
		assertEquals("add second", history.next().getFullMessage());
		assertEquals("add first", history.next().getFullMessage());
		assertFalse(history.hasNext());

		ReflogReader reader = db.getReflogReader(Constants.HEAD);
		assertTrue(reader.getLastEntry().getComment()
				.startsWith("revert: Revert \""));
		reader = db.getReflogReader(db.getBranch());
		assertTrue(reader.getLastEntry().getComment()
				.startsWith("revert: Revert \""));
