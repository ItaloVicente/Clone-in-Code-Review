		try (Git git = new Git(db)) {
			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			RevCommit initialCommit = git.commit().setMessage("initial").call();

			createBranch(initialCommit
			checkoutBranch("refs/heads/side");

			assertTrue(new File(db.getWorkTree()
			git.add().addFilepattern("a").setUpdate(true).call();
			RevCommit secondCommit = git.commit().setMessage("side").call();

			assertFalse(new File(db.getWorkTree()
			checkoutBranch("refs/heads/master");
			assertTrue(new File(db.getWorkTree()

			assertTrue(new File(db.getWorkTree()
			git.add().addFilepattern("a").setUpdate(true).call();
			git.commit().setMessage("main").call();

			MergeResult result = git.merge().include(secondCommit.getId())
					.setStrategy(MergeStrategy.RESOLVE).call();
			assertEquals(MergeStatus.MERGED
		}
