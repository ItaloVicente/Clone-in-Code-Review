		Git git = new Git(db);

		commitFile("file", "1\n2\n3\n", "master");
		commitFile("file", "1\n2\n3\n", "side");
		checkoutBranch("refs/heads/side");
		RevCommit commitD = commitFile("file", "1\n2\n3\n4\n5\n", "side2");
		commitFile("file", "a\n2\n3\n", "side");
		MergeResult mergeResult = git.merge().include(commitD).call();
		ObjectId commitM = mergeResult.getNewHead();
		checkoutBranch("refs/heads/master");
		RevCommit commitT = commitFile("another", "t", "master");

		try {
			git.cherryPick().include(commitM).call();
			fail("merges should not be cherry-picked by default");
		} catch (MultipleParentsNotAllowedException e) {
