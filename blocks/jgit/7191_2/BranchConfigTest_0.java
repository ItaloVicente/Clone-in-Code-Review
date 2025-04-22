	@Test
	public void getTrackingBranchShouldReturnMergeBranchForLocalBranch() {
				+ "[remote \"origin\"]\n"
				+ "[branch \"master\"]\n"
				+ "  remote = .\n"
				+ "  merge = refs/heads/master\n");

		BranchConfig branchConfig = new BranchConfig(c
		assertEquals("refs/heads/master"
				branchConfig.getTrackingBranch());
	}

	@Test
	public void getTrackingBranchShouldReturnNullWithoutMergeBranchForLocalBranch() {
				+ "[remote \"origin\"]\n"
				+ "  fetch = +refs/heads/onlyone:refs/remotes/origin/onlyone\n"
				+ "  remote = .\n");
		BranchConfig branchConfig = new BranchConfig(c
		assertNull(branchConfig.getTrackingBranch());
	}

	@Test
	public void getTrackingBranchShouldHandleNormalCaseForRemoteTrackingBranch() {
				+ "[remote \"origin\"]\n"
				+ "[branch \"master\"]\n"
				+ "  remote = origin\n"
				+ "  merge = refs/heads/master\n");

		BranchConfig branchConfig = new BranchConfig(c
		assertEquals("refs/remotes/origin/master"
				branchConfig.getTrackingBranch());
	}

