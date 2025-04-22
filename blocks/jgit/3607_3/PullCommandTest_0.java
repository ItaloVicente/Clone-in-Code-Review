	@Test
	public void testPullWithRemoteName() throws Exception {
		StoredConfig targetConfig = dbTarget.getConfig();
		RemoteConfig config = new RemoteConfig(targetConfig
		config.addURI(new URIish(source.getRepository().getWorkTree().getPath()));
		config.addFetchRefSpec(new RefSpec(
		config.update(targetConfig);
		targetConfig.save();
		PullResult res = target.pull().setRemote("explicit").call();
		assertEquals("explicit"
	}

	@Test
	public void testPullWithRemoteUri() throws Exception {
		String remote = source.getRepository().getWorkTree().getPath();
		PullResult res = target.pull().setRemote(remote).add(Constants.MASTER)
				.call();
		assertEquals(remote
	}

