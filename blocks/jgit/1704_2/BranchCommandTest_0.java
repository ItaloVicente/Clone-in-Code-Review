		git.branchDelete().setBranchNames("ForDelete").setForce(true).call();
		try {
			git.branchDelete().setBranchNames("master").call();
			fail("Deletion of checked out branch without force should have failed");
		} catch (CannotDeleteCurrentBranchException e) {
		}
		try {
			git.branchDelete().setBranchNames("master").setForce(true).call();
			fail("Deletion of checked out branch with force should have failed");
		} catch (CannotDeleteCurrentBranchException e) {
		}
