	@Test
	public void testDeleteBranch() throws Exception {
		String branch = "new-branch";
		git.branchCreate().setName(branch).call();

		String destRef = Constants.R_HEADS + branch;
		git.push().setRefSpecs(new RefSpec().setSource(branch).setDestination(destRef)).call();

		git.branchDelete().setBranchNames(branch).setForce(true).call();
		git.push().setRefSpecs(new RefSpec().setSource(null).setDestination(destRef)).call();

		assertTrue(server.getRequests().isEmpty());
	}
