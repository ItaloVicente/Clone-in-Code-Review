		try (Git git = new Git(db)) {
			writeTrashFile("a"
			writeTrashFile("b"
			git.add().addFilepattern("a").addFilepattern("b").call();
			RevCommit initialCommit = git.commit().setMessage("initial").call();

			createBranch(initialCommit
			checkoutBranch("refs/heads/side");
			writeTrashFile("a"
			writeTrashFile("b"
			git.add().addFilepattern("a").addFilepattern("b").call();
			RevCommit secondCommit = git.commit().setMessage("side").call();

			checkoutBranch("refs/heads/master");
			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			git.commit().setMessage("main").call();

			MergeResult result = git.merge().include(secondCommit.getId())
					.setStrategy(MergeStrategy.RESOLVE).call();
			assertEquals(MergeStatus.CONFLICTING
		}
