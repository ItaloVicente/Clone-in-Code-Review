	@Test
	public void testNoFastForward() throws Exception {
		Git git = new Git(db);
		RevCommit initialCommit = git.commit().setMessage("initial commit")
				.call();
		createBranch(initialCommit
		git.commit().setMessage("second commit").call();
		checkoutBranch("refs/heads/branch1");

		MergeCommand merge = git.merge();
		merge.setFastForward(FastForwardMode.NO_FF);
		merge.include(db.getRef(Constants.MASTER));
		MergeResult result = merge.call();

		assertEquals(MergeStatus.MERGED
	}

	@Test
	public void testFastForwardOnlyNotPossible() throws Exception {
		Git git = new Git(db);
		RevCommit initialCommit = git.commit().setMessage("initial commit")
				.call();
		createBranch(initialCommit
		git.commit().setMessage("second commit").call();
		checkoutBranch("refs/heads/branch1");
		writeTrashFile("file1"
		git.add().addFilepattern("file").call();
		git.commit().setMessage("second commit on branch1").call();
		MergeCommand merge = git.merge();
		merge.setFastForward(FastForwardMode.FF_ONLY);
		merge.include(db.getRef(Constants.MASTER));
		MergeResult result = merge.call();

		assertEquals(MergeStatus.ABORTED
	}
