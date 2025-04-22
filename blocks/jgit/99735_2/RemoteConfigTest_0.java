
	@Test
	public void pushInsteadOfNoPushUrl() throws Exception {
		config.setString("remote"
		config.setStringList("url"
				"pushInsteadOf"
		RemoteConfig rc = new RemoteConfig(config
		assertFalse(rc.getPushURIs().isEmpty());
				rc.getPushURIs().get(0).toASCIIString());
	}
