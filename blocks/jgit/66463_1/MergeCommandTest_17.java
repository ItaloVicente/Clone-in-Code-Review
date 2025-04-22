		try (Git git = new Git(db)) {
			File fileA = writeTrashFile("a"
			RevCommit initialCommit = addAllAndCommit(git);

			createBranch(initialCommit
			checkoutBranch("refs/heads/side");
			write(fileA
			writeTrashFile("b"
			RevCommit sideCommit = addAllAndCommit(git);

			checkoutBranch("refs/heads/master");
			write(fileA
			writeTrashFile("c"
			addAllAndCommit(git);

			write(fileA
			git.add().addFilepattern("a").call();

			String indexState = indexState(CONTENT);

			MergeResult result = git.merge().include(sideCommit.getId())
					.setStrategy(MergeStrategy.RESOLVE).call();

			checkMergeFailedResult(result
					indexState
		}
