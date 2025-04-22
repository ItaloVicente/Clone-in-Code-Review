		writeTrashFile("a", "first line\nsec. line\nthird line\n");
		git.add().addFilepattern("a").call();
		git.commit().setMessage("create a").call();

		writeTrashFile("b", "content\n");
		git.add().addFilepattern("b").call();
		git.commit().setMessage("create b").call();

		writeTrashFile("a", "first line\nsec. line\nthird line\nfourth line\n");
		git.add().addFilepattern("a").call();
		git.commit().setMessage("enlarged a").call();

		writeTrashFile("a",
				"first line\nsecond line\nthird line\nfourth line\n");
		git.add().addFilepattern("a").call();
		RevCommit fixingA = git.commit().setMessage("fixed a").call();

		writeTrashFile("b", "first line\n");
		git.add().addFilepattern("b").call();
		git.commit().setMessage("fixed b").call();

		git.revert().include(fixingA).call();

		assertEquals(RepositoryState.SAFE, db.getRepositoryState());

		assertTrue(new File(db.getWorkTree(), "b").exists());
		checkFile(new File(db.getWorkTree(), "a"),
				"first line\nsec. line\nthird line\nfourth line\n");
		Iterator<RevCommit> history = git.log().call().iterator();
		RevCommit revertCommit = history.next();
		String expectedMessage = "Revert \"fixed a\"\n\n"
				+ "This reverts commit " + fixingA.getId().getName() + ".\n";
		assertEquals(expectedMessage, revertCommit.getFullMessage());
		assertEquals("fixed b", history.next().getFullMessage());
		assertEquals("fixed a", history.next().getFullMessage());
		assertEquals("enlarged a", history.next().getFullMessage());
		assertEquals("create b", history.next().getFullMessage());
		assertEquals("create a", history.next().getFullMessage());
		assertFalse(history.hasNext());

		ReflogReader reader = db.getReflogReader(Constants.HEAD);
		assertTrue(reader.getLastEntry().getComment()
				.startsWith("revert: Revert \""));
		reader = db.getReflogReader(db.getBranch());
		assertTrue(reader.getLastEntry().getComment()
				.startsWith("revert: Revert \""));
