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

