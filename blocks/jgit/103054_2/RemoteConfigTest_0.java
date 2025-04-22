		assertEquals("short:project.git"
				rc.getPushURIs().get(0).toASCIIString());
	}

	@Test
	public void pushInsteadOfAppliedToUri() throws Exception {
		config.setString("remote"
		config.setString("url"
				"short:");
		RemoteConfig rc = new RemoteConfig(config
		assertFalse(rc.getPushURIs().isEmpty());
				rc.getPushURIs().get(0).toASCIIString());
