		Git git = new Git(db);
		RevCommit initialCommit = git.commit().setMessage("initial commit")
				.call();
		createBranch(initialCommit, "refs/heads/branch1");
		git.commit().setMessage("second commit").call();
		checkoutBranch("refs/heads/branch1");
		writeTrashFile("file1", "branch1");
		git.add().addFilepattern("file").call();
		git.commit().setMessage("second commit on branch1").call();
		MergeCommand merge = git.merge();
		merge.setFastForward(FastForwardMode.FF_ONLY);
		merge.include(db.exactRef(R_HEADS + MASTER));
		MergeResult result = merge.call();

		assertEquals(MergeStatus.ABORTED, result.getMergeStatus());
