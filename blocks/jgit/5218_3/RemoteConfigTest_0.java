
	@Test
	public void noInsteadOf() throws Exception {
		config.setString("remote"
		config.setString("url"
		RemoteConfig rc = new RemoteConfig(config
		assertFalse(rc.getURIs().isEmpty());
		assertEquals("short:project.git"
	}

	@Test
	public void singleInsteadOf() throws Exception {
		config.setString("remote"
		config.setString("url"
		RemoteConfig rc = new RemoteConfig(config
		assertFalse(rc.getURIs().isEmpty());
				.toASCIIString());
	}

	@Test
	public void multipleInsteadOf() throws Exception {
		config.setString("remote"
		config.setStringList("url"
				Arrays.asList("pre"
		RemoteConfig rc = new RemoteConfig(config
		assertFalse(rc.getURIs().isEmpty());
				.toASCIIString());
	}

	@Test
	public void noPushInsteadOf() throws Exception {
		config.setString("remote"
		config.setString("url"
		RemoteConfig rc = new RemoteConfig(config
		assertFalse(rc.getPushURIs().isEmpty());
		assertEquals("short:project.git"
				.toASCIIString());
	}

	@Test
	public void singlePushInsteadOf() throws Exception {
		config.setString("remote"
		config.setString("url"
				"short:");
		RemoteConfig rc = new RemoteConfig(config
		assertFalse(rc.getPushURIs().isEmpty());
				.get(0).toASCIIString());
	}

	@Test
	public void multiplePushInsteadOf() throws Exception {
		config.setString("remote"
		config.setStringList("url"
				Arrays.asList("pre"
		RemoteConfig rc = new RemoteConfig(config
		assertFalse(rc.getPushURIs().isEmpty());
				.get(0).toASCIIString());
	}
