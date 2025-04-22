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
			RevCommit thirdCommit = git.commit().setMessage("main").call();

			MergeResult result = git.merge().include(secondCommit.getId())
					.setCommit(false)
					.setStrategy(MergeStrategy.RESOLVE).call();
			assertEquals(MergeStatus.MERGED_NOT_COMMITTED
			assertEquals(db.exactRef(Constants.HEAD).getTarget().getObjectId()
					thirdCommit.getId());

			assertEquals("1(side)\na\n3(main)\n"
					"a")));
			assertEquals("1\nb(side)\n3\n"
			assertEquals("1\nc(main)\n3\n"
					read(new File(db.getWorkTree()

			assertEquals(null

			assertEquals(2
			assertEquals(thirdCommit
			assertEquals(secondCommit
			assertNull(result.getNewHead());
			assertEquals(RepositoryState.MERGING_RESOLVED
		}
