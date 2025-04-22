
	@Test
	public void testPubKeyAcceptedAlgorithms() throws Exception {
		config("Host=orcz\n\tPubkeyAcceptedAlgorithms ^ssh-rsa");
		Host h = osc.lookup("orcz");
		Config c = h.getConfig();
		assertEquals("^ssh-rsa"
				c.getValue(SshConstants.PUBKEY_ACCEPTED_ALGORITHMS));
		assertEquals("^ssh-rsa"
	}

	@Test
	public void testPubKeyAcceptedKeyTypes() throws Exception {
		config("Host=orcz\n\tPubkeyAcceptedKeyTypes ^ssh-rsa");
		Host h = osc.lookup("orcz");
		Config c = h.getConfig();
		assertEquals("^ssh-rsa"
				c.getValue(SshConstants.PUBKEY_ACCEPTED_ALGORITHMS));
		assertEquals("^ssh-rsa"
	}

	@Test
	public void testEolComments() throws Exception {
		config("#Comment\nHost=orcz #Comment\n\tPubkeyAcceptedAlgorithms ^ssh-rsa # Comment\n#Comment");
		Host h = osc.lookup("orcz");
		assertNotNull(h);
		Config c = h.getConfig();
		assertEquals("^ssh-rsa"
				c.getValue(SshConstants.PUBKEY_ACCEPTED_ALGORITHMS));
	}
