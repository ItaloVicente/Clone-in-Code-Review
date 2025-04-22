	@Test
	public void testIdentityAgentNone() throws Exception {
		config("Host orcz\nIdentityAgent none\n");
		HostConfig h = lookup("orcz");
		assertEquals(SshConstants.NONE
				h.getValue(SshConstants.IDENTITY_AGENT));
	}

