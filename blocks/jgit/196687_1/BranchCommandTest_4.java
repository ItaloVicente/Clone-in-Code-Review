	@Test
	void testInvalidBranchHEAD() throws Exception {
		assertThrows(InvalidRefNameException.class
			git.branchCreate().setName("HEAD").call();
			fail("Create branch with invalid ref name should fail");
		});
