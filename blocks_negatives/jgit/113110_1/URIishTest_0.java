	@Test
	public void testMissingPort() throws URISyntaxException {
		URIish u = new URIish(incorrectSshUrl);
		assertFalse(TransportGitSsh.PROTO_SSH.canHandle(u));
	}

