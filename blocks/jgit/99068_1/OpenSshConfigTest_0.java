
	@Test
	public void testHomeDirUserReplacement() throws Exception {
		config("Host=orcz\n\tIdentityFile %d/.ssh/%u_id_dsa");
		final Host h = osc.lookup("orcz");
		assertNotNull(h);
		assertEquals(new File(new File(home
				h.getIdentityFile());
	}

	@Test
	public void testHostnameReplacement() throws Exception {
		config("Host=orcz\nHost *.*\n\tHostname %h\nHost *\n\tHostname %h.example.org");
		final Host h = osc.lookup("orcz");
		assertNotNull(h);
		assertEquals("orcz.example.org"
	}

	@Test
	public void testRemoteUserReplacement() throws Exception {
		config("Host=orcz\n\tUser foo\n" + "Host *.*\n\tHostname %h\n"
				+ "Host *\n\tHostname %h.ex%%20ample.org\n\tIdentityFile ~/.ssh/%h_%r_id_dsa");
		final Host h = osc.lookup("orcz");
		assertNotNull(h);
		assertEquals(
				new File(new File(home
						"orcz.ex%20ample.org_foo_id_dsa")
				h.getIdentityFile());
	}

	@Test
	public void testLocalhostFQDNReplacement() throws Exception {
		String localhost = SystemReader.getInstance().getHostname();
		config("Host=orcz\n\tIdentityFile ~/.ssh/%l_id_dsa");
		final Host h = osc.lookup("orcz");
		assertNotNull(h);
		assertEquals(
				new File(new File(home
				h.getIdentityFile());
	}
