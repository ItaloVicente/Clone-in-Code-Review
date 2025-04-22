		assertEquals(new File(new File(tmp
				h.getValue(SshConstants.IDENTITY_AGENT));
	}

	@Test
	public void testIdentityAgentNone() throws Exception {
		config("Host orcz\nIdentityAgent none\n");
		HostConfig h = lookup("orcz");
		assertEquals(SshConstants.NONE
				h.getValue(SshConstants.IDENTITY_AGENT));
	}

	@Test
	public void testIdentityAgentSshAuthSock() throws Exception {
		config("Host orcz\nIdentityAgent SSH_AUTH_SOCK\n");
		HostConfig h = lookup("orcz");
		assertEquals(SshConstants.ENV_SSH_AUTH_SOCKET
