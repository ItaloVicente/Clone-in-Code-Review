
	@Test
	public void testPushWithSessionLimit() throws Exception {
		server.getProperties().put(ServerFactoryManager.MAX_CONCURRENT_SESSIONS
				Integer.valueOf(2));
				defaultCloneDir
				"Host localhost"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"IdentityFile " + privateKey1.getAbsolutePath());
		try (Git git = Git.open(localClone)) {
			git.fetch().call();
			git.fetch().call();
		}
	}
