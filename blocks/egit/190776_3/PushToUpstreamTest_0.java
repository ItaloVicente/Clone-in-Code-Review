	@Test
	public void pushWithOriginConfig() throws Exception {
		checkoutNewLocalBranch("foo");
		String remoteName = "origin";
		repository.getConfig().setString("remote", remoteName, "url",
				repository.getConfig().getString("remote", "push", "pushurl"));
		repository.getConfig().setString("remote", remoteName, "fetch",
				"refs/heads/*:refs/remotes/origin/*");
		pushToUpstream("origin", "foo", true, false);
		assertBranchPushed("foo", remoteRepository);
	}

	@Test
	public void pushIsDisabledWithPushDefaultNothing() throws Exception {
		checkoutNewLocalBranch("foo");
		repository.getConfig().setString(ConfigConstants.CONFIG_PUSH_SECTION,
				null, ConfigConstants.CONFIG_KEY_DEFAULT, "nothing");
		String remoteName = "origin";
		repository.getConfig().setString("remote", remoteName, "url",
				repository.getConfig().getString("remote", "push", "pushurl"));
		repository.getConfig().setString("remote", remoteName, "fetch",
				"refs/heads/*:refs/remotes/origin/*");
		assertPushToUpstreamDisabled("origin");
	}

