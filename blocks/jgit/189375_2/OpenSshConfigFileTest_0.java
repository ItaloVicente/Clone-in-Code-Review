	@Test
	public void testIdentityAgentSshAuthSock() throws Exception {
		config("Host orcz\nIdentityAgent SSH_AUTH_SOCK\n");
		HostConfig h = lookup("orcz");
		assertEquals(SshConstants.ENV_SSH_AUTH_SOCKET
				h.getValue(SshConstants.IDENTITY_AGENT));
	}

