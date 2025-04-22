		writeTrashFile("a", "first\nsecond\nthird\nfourth\n");
		git.add().addFilepattern("a").call();
		RevCommit fourthCommit = git.commit().setMessage("add fourth").call();

		git.revert().include(fourthCommit).include(secondCommit).call();

		assertEquals(RepositoryState.REVERTING, db.getRepositoryState());

		checkFile(new File(db.getWorkTree(), "a"), "first\n"
				+ "<<<<<<< master\n" + "second\n" + "third\n" + "=======\n"
				+ ">>>>>>> " + secondCommit.getId().abbreviate(7).name()
				+ " add second\n");
		Iterator<RevCommit> history = git.log().call().iterator();
		RevCommit revertCommit = history.next();
		String expectedMessage = "Revert \"add fourth\"\n\n"
				+ "This reverts commit " + fourthCommit.getId().getName()
				+ ".\n";
		assertEquals(expectedMessage, revertCommit.getFullMessage());
		assertEquals("add fourth", history.next().getFullMessage());
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
