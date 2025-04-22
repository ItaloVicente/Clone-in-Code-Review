	@Theory
	public void checkFileDirMergeConflictInVirtualAncestor_NoConflictInChildren(
			MergeStrategy strategy)
			throws Exception {
		if (!strategy.equals(MergeStrategy.RECURSIVE)) {
			return;
		}

		Git git = Git.wrap(db);

		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		RevCommit commitI = git.commit().setMessage("Initial commit").call();

		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		RevCommit commitA1 = git.commit().setMessage("Ancestor 1").call();

		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("Child 1 on master").call();

		git.checkout().setCreateBranch(true).setStartPoint(commitI).setName("branch-to-merge").call();
		git.rm().addFilepattern("a").call();
		writeTrashFile("a/content"
		git.add().addFilepattern("a/content").call();
		RevCommit commitA2 = git.commit().setMessage("Ancestor 2").call();

		git.checkout().setCreateBranch(true).setStartPoint(commitA1).setName("second-branch").call();
		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("Child 2 on second-branch").call();

		MergeResult mergeResult = git.merge().include(commitA2).setStrategy(strategy).call();
		assertEquals(mergeResult.getNewHead()
		assertEquals(mergeResult.getMergeStatus()
		git.rm().addFilepattern("a").call();
		git.rm().addFilepattern("a/content").call();
		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		RevCommit commitC3S = git.commit().setMessage("Child 3 on second bug - resolve merge conflict")
				.call();

		git.checkout().setName("master").call();
		mergeResult = git.merge().include(commitA2).setStrategy(strategy).call();
		assertEquals(mergeResult.getNewHead()
		assertEquals(mergeResult.getMergeStatus()

		git.rm().addFilepattern("a").call();
		git.rm().addFilepattern("a/content").call();
		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("Child 4 on master - resolve merge conflict").call();

		mergeResult = git.merge().include(commitC3S).call();
		assertEquals(mergeResult.getMergeStatus()

	}

	@Theory
	public void checkFileDirMergeConflictInVirtualAncestor_ConflictInChildren_FileDir(MergeStrategy strategy)
			throws Exception {
		if (!strategy.equals(MergeStrategy.RECURSIVE)) {
			return;
		}

		Git git = Git.wrap(db);

		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		RevCommit commitI = git.commit().setMessage("Initial commit").call();

		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		RevCommit commitA1 = git.commit().setMessage("Ancestor 1").call();

		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("Child 1 on master").call();

		git.checkout().setCreateBranch(true).setStartPoint(commitI).setName("branch-to-merge").call();

		git.rm().addFilepattern("a").call();
		writeTrashFile("a/content"
		git.add().addFilepattern("a/content").call();
		RevCommit commitA2 = git.commit().setMessage("Ancestor 2").call();

		git.checkout().setCreateBranch(true).setStartPoint(commitA1).setName("second-branch").call();
		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("Child 2 on second-branch").call();

		MergeResult mergeResult = git.merge().include(commitA2).setStrategy(strategy).call();
		assertEquals(mergeResult.getNewHead()
		assertEquals(mergeResult.getMergeStatus()
		git.rm().addFilepattern("a").call();
		git.rm().addFilepattern("a/content").call();
		writeTrashFile("a"
				"content in Child 3 (commited on second-branch) - merge conflict resolution");
		git.add().addFilepattern("a").call();
		RevCommit commitC3S = git.commit().setMessage("Child 3 on second bug - resolve merge conflict")
				.call();

		git.checkout().setName("master").call();
		mergeResult = git.merge().include(commitA2).setStrategy(strategy).call();
		assertEquals(mergeResult.getNewHead()
		assertEquals(mergeResult.getMergeStatus()

		git.rm().addFilepattern("a").call();
		git.rm().addFilepattern("a/content").call();
		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("Child 4 on master - resolve merge conflict").call();

		mergeResult = git.merge().include(commitC3S).call();
		assertEquals(mergeResult.getMergeStatus()
		String expected =
				"<<<<<<< HEAD\n" + "content in Child 4 (commited on master) - merge conflict resolution\n"
						+ "=======\n"
						+ "content in Child 3 (commited on second-branch) - merge conflict resolution\n"
						+ ">>>>>>> " + commitC3S.name() + "\n";
		assertEquals(expected
		assertEquals(
				"[a
				indexState(CONTENT));
	}

	@Theory
	public void checkFileDirMergeConflictInVirtualAncestor_ConflictInChildren_DirFile(MergeStrategy strategy)
			throws Exception {
		if (!strategy.equals(MergeStrategy.RECURSIVE)) {
			return;
		}

		Git git = Git.wrap(db);

		writeTrashFile("a/content"
		git.add().addFilepattern("a/content").call();
		RevCommit commitI = git.commit().setMessage("Initial commit").call();

		writeTrashFile("a/content"
		git.add().addFilepattern("a/content").call();
		RevCommit commitA1 = git.commit().setMessage("Ancestor 1").call();

		writeTrashFile("a/content"
		git.add().addFilepattern("a/content").call();
		git.commit().setMessage("Child 1 on master").call();

		git.checkout().setCreateBranch(true).setStartPoint(commitI).setName("branch-to-merge").call();

		git.rm().addFilepattern("a/content").call();
		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		RevCommit commitA2 = git.commit().setMessage("Ancestor 2").call();

		git.checkout().setCreateBranch(true).setStartPoint(commitA1).setName("second-branch").call();
		writeTrashFile("a/content"
		git.add().addFilepattern("a/content").call();
		git.commit().setMessage("Child 2 on second-branch").call();

		MergeResult mergeResult = git.merge().include(commitA2).setStrategy(strategy).call();
		assertEquals(mergeResult.getNewHead()
		assertEquals(mergeResult.getMergeStatus()
		git.rm().addFilepattern("a").call();
		git.rm().addFilepattern("a/content").call();
		deleteTrashFile("a/content");
		deleteTrashFile("a");
		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		RevCommit commitC3S = git.commit().setMessage("Child 3 on second bug - resolve merge conflict").call();

		git.checkout().setName("master").call();
		mergeResult = git.merge().include(commitA2).setStrategy(strategy).call();
		assertEquals(mergeResult.getNewHead()
		assertEquals(mergeResult.getMergeStatus()

		git.rm().addFilepattern("a").call();
		git.rm().addFilepattern("a/content").call();
		deleteTrashFile("a/content");
		deleteTrashFile("a");
		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("Child 4 on master - resolve merge conflict").call();

		mergeResult = git.merge().include(commitC3S).call();
		assertEquals(mergeResult.getMergeStatus()
		String expected = "<<<<<<< HEAD\n" + "content in Child 4 (commited on master) - merge conflict resolution\n"
				+ "=======\n" + "content in Child 3 (commited on second-branch) - merge conflict resolution\n"
				+ ">>>>>>> " + commitC3S.name() + "\n";
		assertEquals(expected
		assertEquals(
				"[a
				indexState(CONTENT));
	}

