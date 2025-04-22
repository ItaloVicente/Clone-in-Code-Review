	@Theory
	public void checkUntrackedFolderIsNotAConflict(
			MergeStrategy strategy) throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("d/1"
		git.add().addFilepattern("d/1").call();
		RevCommit first = git.commit().setMessage("added d/1").call();

		writeTrashFile("e/1"
		git.add().addFilepattern("e/1").call();
		RevCommit masterCommit = git.commit().setMessage("added e/1").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();
		writeTrashFile("f/1"
		git.add().addFilepattern("f/1").call();
		git.commit().setAll(true).setMessage("added f/1")
				.call();

		writeTrashFile("e/2"

		MergeResult mergeRes = git.merge().setStrategy(strategy)
				.include(masterCommit).call();
		assertEquals(MergeStatus.MERGED
		assertEquals(
				"[d/1
				indexState(CONTENT));
	}

	@Theory
	public void checkUntrackedEmpytFolderIsNotAConflictWithFile(
			MergeStrategy strategy)
			throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("d/1"
		git.add().addFilepattern("d/1").call();
		RevCommit first = git.commit().setMessage("added d/1").call();

		writeTrashFile("e"
		git.add().addFilepattern("e").call();
		RevCommit masterCommit = git.commit().setMessage("added e").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();
		writeTrashFile("f/1"
		git.add().addFilepattern("f/1").call();
		git.commit().setAll(true).setMessage("added f/1").call();

		FileUtils.mkdirs(new File(trash

		MergeResult mergeRes = git.merge().setStrategy(strategy)
				.include(masterCommit).call();
		assertEquals(MergeStatus.MERGED
		assertEquals(
				"[d/1
				indexState(CONTENT));
	}

