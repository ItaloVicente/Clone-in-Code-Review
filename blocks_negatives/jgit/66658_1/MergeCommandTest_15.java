		Git git = new Git(db);

		writeTrashFile("x", "add x");
		git.add().addFilepattern("x").call();
		RevCommit initial = git.commit().setMessage("add x").call();

		createBranch(initial, "refs/heads/d1");
		createBranch(initial, "refs/heads/d2");

		checkoutBranch("refs/heads/d1");
		new File(db.getWorkTree(), "x")
				.renameTo(new File(db.getWorkTree(), "y"));
		git.rm().addFilepattern("x").call();
		git.add().addFilepattern("y").call();
		RevCommit d1Commit = git.commit().setMessage("d1 rename x -> y").call();

		checkoutBranch("refs/heads/d2");
		writeTrashFile("x", "d2 change");
		git.add().addFilepattern("x").call();
		RevCommit d2Commit = git.commit().setMessage("d2 change in x").call();

		checkoutBranch("refs/heads/master");
		MergeResult d1Merge = git.merge().include(d1Commit).call();
		assertEquals(MergeResult.MergeStatus.FAST_FORWARD,
				d1Merge.getMergeStatus());

		MergeResult d2Merge = git.merge().include(d2Commit).call();
		assertEquals(MergeResult.MergeStatus.CONFLICTING,
				d2Merge.getMergeStatus());
		assertEquals(1, d2Merge.getConflicts().size());
		assertEquals(3, d2Merge.getConflicts().get("x")[0].length);
