	@Theory
	public void checkFileReplacedByFolderInTheirs(MergeStrategy strategy)
			throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("sub"
		git.add().addFilepattern("sub").call();
		RevCommit first = git.commit().setMessage("initial").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();

		git.rm().addFilepattern("sub").call();
		writeTrashFile("sub/file"
		git.add().addFilepattern("sub/file").call();
		RevCommit masterCommit = git.commit().setMessage("file -> folder")
				.call();

		git.checkout().setName("master").call();
		writeTrashFile("noop"
		git.add().addFilepattern("noop").call();
		git.commit().setAll(true).setMessage("noop").call();

		MergeResult mergeRes = git.merge().setStrategy(strategy)
				.include(masterCommit).call();
		assertEquals(MergeStatus.MERGED
		assertEquals(
				"[noop
				indexState(CONTENT));
	}

	@Theory
	public void checkFileReplacedByFolderInOurs(MergeStrategy strategy)
			throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("sub"
		git.add().addFilepattern("sub").call();
		RevCommit first = git.commit().setMessage("initial").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();
		writeTrashFile("noop"
		git.add().addFilepattern("noop").call();
		RevCommit sideCommit = git.commit().setAll(true).setMessage("noop")
				.call();

		git.checkout().setName("master").call();
		git.rm().addFilepattern("sub").call();
		writeTrashFile("sub/file"
		git.add().addFilepattern("sub/file").call();
		git.commit().setMessage("file -> folder")
				.call();

		MergeResult mergeRes = git.merge().setStrategy(strategy)
				.include(sideCommit).call();
		assertEquals(MergeStatus.MERGED
		assertEquals(
				"[noop
				indexState(CONTENT));
	}

