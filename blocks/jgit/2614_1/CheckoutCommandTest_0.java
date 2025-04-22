
	@Test
	public void testCheckoutRemoteTrackingWithoutLocalBranch() {
		try {
			Repository db2 = createWorkRepository();
			Git git2 = new Git(db2);

			final StoredConfig config = db2.getConfig();
			RemoteConfig remoteConfig = new RemoteConfig(config
			URIish uri = new URIish(db.getDirectory().toURI().toURL());
			remoteConfig.addURI(uri);
			remoteConfig.update(config);
			config.save();

			git2.fetch().setRemote("origin").setRefSpecs(spec).call();
			git2.checkout().setName("remotes/origin/test").call();
			assertEquals("[Test.txt
					indexState(db2
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
