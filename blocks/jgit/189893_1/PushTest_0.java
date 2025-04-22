	@Test
	public void testDeleteBranch() throws Exception {
		String branch = "new-branch";
		git.branchCreate().setName(branch).call();
		git.push().setRefSpecs(new RefSpec().setSource(branch).setDestination(Constants.R_HEADS + branch)).call();

		git.branchDelete().setBranchNames(branch).setForce(true).call();
		git.push().setRefSpecs(new RefSpec().setSource(null).setDestination(Constants.R_HEADS + branch)).call();

		assertTrue(server.getRequests().isEmpty());
	}
