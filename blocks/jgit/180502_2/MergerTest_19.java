	@Theory
	public void checkModeMergeConflictInVirtualAncestor(MergeStrategy strategy) throws Exception {
		if (!strategy.equals(MergeStrategy.RECURSIVE)) {
			return;
		}

		Git git = Git.wrap(db);

		writeTrashFile("c"
		git.add().addFilepattern("c").call();
		RevCommit commitI = git.commit().setMessage("Initial commit").call();

		File a = writeTrashFile("a"
		git.add().addFilepattern("a").call();
		RevCommit commitA1 = git.commit().setMessage("Ancestor 1").call();

		a = writeTrashFile("a"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("Child 1 on master").call();

		git.checkout().setCreateBranch(true).setStartPoint(commitI).setName("branch-to-merge").call();
		a = writeTrashFile("a"
		a.setExecutable(true);
		git.add().addFilepattern("a").call();
		RevCommit commitA2 = git.commit().setMessage("Ancestor 2").call();

		git.checkout().setCreateBranch(true).setStartPoint(commitA1).setName("second-branch").call();
		a = writeTrashFile("a"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("Child 2 on second-branch").call();

		MergeResult mergeResult = git.merge().include(commitA2).setStrategy(strategy).call();
		assertEquals(mergeResult.getNewHead()
		assertEquals(mergeResult.getMergeStatus()
		a = writeTrashFile("a"
		a.setExecutable(false);
		git.add().addFilepattern("a").call();
		RevCommit commitC3S = git.commit().setMessage("Child 3 on second bug - resolve merge conflict").call();

		git.checkout().setName("master").call();
		mergeResult = git.merge().include(commitA2).setStrategy(strategy).call();
		assertEquals(mergeResult.getNewHead()
		assertEquals(mergeResult.getMergeStatus()

		a = writeTrashFile("a"
		a.setExecutable(false);
		git.add().addFilepattern("a").call();
		git.commit().setMessage("Child 4 on master - resolve merge conflict").call();

		mergeResult = git.merge().include(commitC3S).call();
		assertEquals(mergeResult.getMergeStatus()

	}

