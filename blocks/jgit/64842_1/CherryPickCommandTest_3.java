		try (Git git = new Git(db)) {
			commitFile("file"
			commitFile("file"
			checkoutBranch("refs/heads/side");
			RevCommit commitD = commitFile("file"
			commitFile("file"
			MergeResult mergeResult = git.merge().include(commitD).call();
			ObjectId commitM = mergeResult.getNewHead();
			checkoutBranch("refs/heads/master");
			RevCommit commitT = commitFile("another"

			try {
				git.cherryPick().include(commitM).call();
				fail("merges should not be cherry-picked by default");
			} catch (MultipleParentsNotAllowedException e) {
			}
			try {
				git.cherryPick().include(commitM).setMainlineParentNumber(3).call();
				fail("specifying a non-existent parent should fail");
			} catch (JGitInternalException e) {
				assertTrue(e.getMessage().endsWith(
						"does not have a parent number 3."));
			}

			CherryPickResult result = git.cherryPick().include(commitM)
					.setMainlineParentNumber(1).call();
			assertEquals(CherryPickStatus.OK
			checkFile(new File(db.getWorkTree()

			git.reset().setMode(ResetType.HARD).setRef(commitT.getName()).call();

			CherryPickResult result2 = git.cherryPick().include(commitM)
					.setMainlineParentNumber(2).call();
			assertEquals(CherryPickStatus.OK
			checkFile(new File(db.getWorkTree()
