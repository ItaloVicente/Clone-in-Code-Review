		Git git = new Git(db);
		RevCommit first = git.commit().setMessage("initial commit").call();
		createBranch(first, "refs/heads/branch1");

		RevCommit second = git.commit().setMessage("second commit").call();
		MergeResult result = git.merge().include(db.exactRef("refs/heads/branch1")).call();
		assertEquals(MergeResult.MergeStatus.ALREADY_UP_TO_DATE, result.getMergeStatus());
		assertEquals(second, result.getNewHead());
