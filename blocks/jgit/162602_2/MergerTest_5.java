	@Theory
	public void checkMergeConflictInVirtualAncestor(
			MergeStrategy strategy) throws Exception {
		if (!strategy.equals(MergeStrategy.RECURSIVE)) {
			return;
		}

		Git git = Git.wrap(db);

		writeTrashFile("a"
		writeTrashFile("b"
		git.add().addFilepattern("a").addFilepattern("b").call();
		RevCommit first = git.commit().setMessage("Initial commit").call();

		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		RevCommit commitY = git.commit().setMessage("Modify a").call();

		git.rm().addFilepattern("a").call();
		writeTrashFile("c"
		git.add().addFilepattern("c").call();
		git.commit().setMessage("Delete modified a").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("merge-both-sides").call();
		git.rm().addFilepattern("a").call();
		RevCommit commitX = git.commit().setMessage("Delete original a").call();

		git.checkout().setCreateBranch(true).setStartPoint(commitY)
				.setName("second-branch").call();
		git.rm().addFilepattern("a").call();
		git.commit().setMessage("Delete modified a").call();

		MergeResult mergeResult = git.merge().include(commitX)
				.setStrategy(strategy)
				.call();
		ObjectId commitB = mergeResult.getNewHead();

		git.checkout().setName("master").call();
		mergeResult = git.merge().include(commitX).setStrategy(strategy)
				.call();

		git.merge().include(commitB).call();
	}

