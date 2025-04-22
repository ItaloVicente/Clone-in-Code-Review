		try (Git git = new Git(db)) {
			writeTrashFile("a"
			writeTrashFile("b"
			writeTrashFile("c/c/c"
			git.add().addFilepattern("a").addFilepattern("b")
					.addFilepattern("c/c/c").call();
			RevCommit initialCommit = git.commit().setMessage("initial").call();

			createBranch(initialCommit
			checkoutBranch("refs/heads/side");

			writeTrashFile("a"
			writeTrashFile("b"
			git.add().addFilepattern("a").addFilepattern("b").call();
			RevCommit secondCommit = git.commit().setMessage("side").call();

			assertEquals("1\nb(side)\n3\n"
			checkoutBranch("refs/heads/master");
			assertEquals("1\nb\n3\n"

			writeTrashFile("a"
			writeTrashFile("c/c/c"
			git.add().addFilepattern("a").addFilepattern("c/c/c").call();
			git.commit().setMessage("main").call();

			writeTrashFile("d"
			assertTrue(new File(db.getWorkTree()

			MergeResult result = git.merge().include(secondCommit.getId())
					.setStrategy(MergeStrategy.RESOLVE).call();
			assertEquals(MergeStatus.CONFLICTING

			assertEquals(
					"1\n<<<<<<< HEAD\na(main)\n=======\na(side)\n>>>>>>> 86503e7e397465588cc267b65d778538bffccb83\n3\n"
					read(new File(db.getWorkTree()
			assertEquals("1\nb(side)\n3\n"
			assertEquals("1\nc(main)\n3\n"
					read(new File(db.getWorkTree()
			assertEquals("1\nd\n3\n"
			File dir = new File(db.getWorkTree()
			assertTrue(dir.isDirectory());

			assertEquals(1
			assertEquals(3

			assertEquals(RepositoryState.MERGING
		}
