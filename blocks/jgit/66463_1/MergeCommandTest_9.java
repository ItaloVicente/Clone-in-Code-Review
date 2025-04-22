		try (Git git = new Git(db)) {
			writeTrashFile("a"
			writeTrashFile("b"
			writeTrashFile("d"
			writeTrashFile("c/c/c"
			git.add().addFilepattern("a").addFilepattern("b")
					.addFilepattern("c/c/c").addFilepattern("d").call();
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

			writeTrashFile("d"
			MergeResult result = git.merge().include(secondCommit.getId())
					.setStrategy(MergeStrategy.RESOLVE).call();
			assertEquals(MergeStatus.MERGED

			assertEquals("1(side)\na\n3(main)\n"
					"a")));
			assertEquals("1\nb(side)\n3\n"
			assertEquals("1\nc(main)\n3\n"
					"c/c/c")));
			assertEquals("--- dirty ---"

			assertEquals(null

			assertEquals(2
			assertEquals(thirdCommit
			assertEquals(secondCommit

			Iterator<RevCommit> it = git.log().call().iterator();
			RevCommit newHead = it.next();
			assertEquals(newHead
			assertEquals(2
			assertEquals(thirdCommit
			assertEquals(secondCommit
			assertEquals(
					"Merge commit '064d54d98a4cdb0fed1802a21c656bfda67fe879'"
					newHead.getFullMessage());

			assertEquals(RepositoryState.SAFE
		}
