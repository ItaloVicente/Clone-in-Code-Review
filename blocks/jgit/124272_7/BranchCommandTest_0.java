	@Test(expected = InvalidRefNameException.class)
	public void testInvalidBranchHEAD() throws Exception {
		git.branchCreate().setName("HEAD").call();
		fail("Create branch with invalid ref name should fail");
	}

	@Test(expected = InvalidRefNameException.class)
	public void testInvalidBranchDash() throws Exception {
		git.branchCreate().setName("-x").call();
		fail("Create branch with invalid ref name should fail");
	}

