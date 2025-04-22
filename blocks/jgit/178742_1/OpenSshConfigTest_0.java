
	@Test
	public void testEnVarSubstitution() throws Exception {
		config("Host orcz\nIdentityFile /tmp/${TST_VAR}\n"
				+ "CertificateFile /tmp/${}/foo\nUser ${TST_VAR}\nIdentityAgent /tmp/${TST_VAR/bar");
		Host h = osc.lookup("orcz");
		assertNotNull(h);
		Config c = h.getConfig();
		assertEquals("/tmp/TEST"
				c.getValue(SshConstants.IDENTITY_FILE));
		assertEquals("/tmp/${}/foo"
		assertEquals("${TST_VAR}"
		assertEquals("${TST_VAR}"
		assertEquals("/tmp/${TST_VAR/bar"
				c.getValue(SshConstants.IDENTITY_AGENT));
	}
