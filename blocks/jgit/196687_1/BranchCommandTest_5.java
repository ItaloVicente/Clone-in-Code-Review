	@Test
	void testInvalidBranchDash() throws Exception {
		assertThrows(InvalidRefNameException.class
			git.branchCreate().setName("-x").call();
			fail("Create branch with invalid ref name should fail");
		});
