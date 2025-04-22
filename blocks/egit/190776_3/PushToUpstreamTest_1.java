	@Test
	public void pushWithExistingUpstreamConfigurationDifferent()
			throws Exception {
		checkoutNewLocalBranch("bar");
		String remoteName = "fetch";
		repository.getConfig().setString(ConfigConstants.CONFIG_BRANCH_SECTION,
				"bar", ConfigConstants.CONFIG_KEY_REMOTE, remoteName);
		repository.getConfig().setString(ConfigConstants.CONFIG_BRANCH_SECTION,
				"bar", ConfigConstants.CONFIG_KEY_MERGE, "refs/heads/bar2");

		pushToUpstream(remoteName, "bar", true, false);
		assertBranchPushed("bar", "bar2", remoteRepository);
	}

	@Test
	public void pushWithExistingUpstreamConfigurationPushDefaultUpstream()
			throws Exception {
		checkoutNewLocalBranch("bar");
		repository.getConfig().setString(ConfigConstants.CONFIG_PUSH_SECTION,
				null, ConfigConstants.CONFIG_KEY_DEFAULT, "upstream");
		String remoteName = "fetch";
		repository.getConfig().setString(ConfigConstants.CONFIG_BRANCH_SECTION,
				"bar", ConfigConstants.CONFIG_KEY_REMOTE, remoteName);
		repository.getConfig().setString(ConfigConstants.CONFIG_BRANCH_SECTION,
				"bar", ConfigConstants.CONFIG_KEY_MERGE, "refs/heads/bar2");

		pushToUpstream(remoteName, "bar", false, false);
		assertBranchPushed("bar", "bar2", remoteRepository);
	}

	@Test
	public void pushWithExistingUpstreamConfigurationPushDefaultCurrent()
			throws Exception {
		checkoutNewLocalBranch("bar");
		repository.getConfig().setString(ConfigConstants.CONFIG_PUSH_SECTION,
				null, ConfigConstants.CONFIG_KEY_DEFAULT, "current");
		String remoteName = "fetch";
		repository.getConfig().setString(ConfigConstants.CONFIG_BRANCH_SECTION,
				"bar", ConfigConstants.CONFIG_KEY_REMOTE, remoteName);
		repository.getConfig().setString(ConfigConstants.CONFIG_BRANCH_SECTION,
				"bar", ConfigConstants.CONFIG_KEY_MERGE, "refs/heads/bar2");

		pushToUpstream(remoteName, "bar", false, false);
		assertBranchPushed("bar", remoteRepository);
	}

