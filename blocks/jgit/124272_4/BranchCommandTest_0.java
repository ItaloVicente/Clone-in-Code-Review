	@Test
	public void testInvalidBranchHEAD() throws Exception {
		try {
			git.branchCreate().setName("HEAD").call();
			fail("Create branch with invalid ref name should fail");
		} catch (InvalidRefNameException e) {
		}
		try {
			git.branchCreate().setName("refs/heads/HEAD").call();
			fail("Create branch with invalid ref name should fail");
		} catch (InvalidRefNameException e) {
		}
	}

	@Test
	public void testInvalidBranchDash() throws Exception {
		try {
			git.branchCreate().setName("-x").call();
			fail("Create branch with invalid ref name should fail");
		} catch (InvalidRefNameException e) {
		}
	}

