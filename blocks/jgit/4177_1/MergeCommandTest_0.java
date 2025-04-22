	@Test
	public void testFastForwardFailsDueToDirtyWorktree() throws Exception {
		Git git = new Git(db);
		File a = writeTrashFile("a"
		writeTrashFile("c"
		RevCommit first = addAllAndCommit(git);

		createBranch(first
		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("second commit").call();

		checkoutBranch("refs/heads/branch1");
		writeTrashFile("a"

		String indexState = indexState(CONTENT);

		MergeResult result = git.merge().include(db.getRef(Constants.MASTER))
				.call();

		checkMergeFailedResult(result
				indexState
	}

