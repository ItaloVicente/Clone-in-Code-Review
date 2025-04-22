		try (Git git = new Git(db)) {
			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			git.commit().setMessage("add first").call();

			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			RevCommit secondCommit = git.commit().setMessage("add second").call();

			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			git.commit().setMessage("add third").call();

			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			RevCommit fourthCommit = git.commit().setMessage("add fourth").call();

			git.revert().include(fourthCommit).include(secondCommit).call();

			assertEquals(RepositoryState.REVERTING

			checkFile(new File(db.getWorkTree()
					+ "<<<<<<< master\n" + "second\n" + "third\n" + "=======\n"
					+ ">>>>>>> " + secondCommit.getId().abbreviate(7).name()
					+ " add second\n");
			Iterator<RevCommit> history = git.log().call().iterator();
			RevCommit revertCommit = history.next();
			String expectedMessage = "Revert \"add fourth\"\n\n"
					+ "This reverts commit " + fourthCommit.getId().getName()
					+ ".\n";
			assertEquals(expectedMessage
			assertEquals("add fourth"
			assertEquals("add third"
			assertEquals("add second"
			assertEquals("add first"
			assertFalse(history.hasNext());
