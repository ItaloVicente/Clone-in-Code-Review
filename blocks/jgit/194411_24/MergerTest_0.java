	@Theory
	public void checkMergeDoesntCrashWithSpecialFileNames(
			MergeStrategy strategy) throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("subtree"
		writeTrashFile("subtree-0"
		git.add().addFilepattern("subtree").call();
		git.add().addFilepattern("subtree-0").call();
		RevCommit toMerge = git.commit().setMessage("commit-1").call();

		git.rm().addFilepattern("subtree").call();
		writeTrashFile("subtree/file"
		git.add().addFilepattern("subtree").call();
		RevCommit mergeTip = git.commit().setMessage("commit2").call();

		ResolveMerger merger = (ResolveMerger) strategy.newMerger(db
		assertTrue(merger.merge(mergeTip
	}

