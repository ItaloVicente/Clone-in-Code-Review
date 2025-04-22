		RemoteConfig cfg = new RemoteConfig(git2.getRepository().getConfig()
				Constants.DEFAULT_REMOTE_NAME);
		List<RefSpec> specs = cfg.getFetchRefSpecs();
		assertEquals(1
		assertEquals(
				new RefSpec("+refs/heads/master:refs/heads/master")
				specs.get(0));
