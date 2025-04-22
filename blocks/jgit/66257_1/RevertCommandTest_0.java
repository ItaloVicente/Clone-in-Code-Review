		try (Git git = new Git(db)) {
			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			git.commit().setMessage("create a").call();

			writeTrashFile("b"
			git.add().addFilepattern("b").call();
			git.commit().setMessage("create b").call();

			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			git.commit().setMessage("enlarged a").call();

			writeTrashFile("a"
					"first line\nsecond line\nthird line\nfourth line\n");
			git.add().addFilepattern("a").call();
			RevCommit fixingA = git.commit().setMessage("fixed a").call();

			writeTrashFile("b"
			git.add().addFilepattern("b").call();
			git.commit().setMessage("fixed b").call();

			git.revert().include(fixingA).call();

			assertEquals(RepositoryState.SAFE

			assertTrue(new File(db.getWorkTree()
			checkFile(new File(db.getWorkTree()
					"first line\nsec. line\nthird line\nfourth line\n");
			Iterator<RevCommit> history = git.log().call().iterator();
			RevCommit revertCommit = history.next();
			String expectedMessage = "Revert \"fixed a\"\n\n"
					+ "This reverts commit " + fixingA.getId().getName() + ".\n";
			assertEquals(expectedMessage
			assertEquals("fixed b"
			assertEquals("fixed a"
			assertEquals("enlarged a"
			assertEquals("create b"
			assertEquals("create a"
			assertFalse(history.hasNext());
