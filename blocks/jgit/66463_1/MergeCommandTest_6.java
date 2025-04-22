		try (Git git = new Git(db)) {
			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			RevCommit initialCommit = git.commit().setMessage("initial").call();

			createBranch(initialCommit
			checkoutBranch("refs/heads/side");

			writeTrashFile("b"
			git.add().addFilepattern("b").call();
			RevCommit secondCommit = git.commit().setMessage("side").call();

			checkoutBranch("refs/heads/master");

			writeTrashFile("b"
			git.add().addFilepattern("b").call();
			git.commit().setMessage("main").call();

			MergeResult result = git.merge().include(secondCommit.getId())
					.setStrategy(MergeStrategy.RESOLVE).call();
			assertEquals(MergeStatus.MERGED
			assertEquals("1\nb(1)\n3\n"
			assertEquals("merge " + secondCommit.getId().getName()
					+ ": Merge made by resolve."
					.getReflogReader(Constants.HEAD)
					.getLastEntry().getComment());
			assertEquals("merge " + secondCommit.getId().getName()
					+ ": Merge made by resolve."
					.getReflogReader(db.getBranch())
					.getLastEntry().getComment());
		}
