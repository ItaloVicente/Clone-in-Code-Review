
	@Test
	public void testDefaultBlock() throws Exception {
		config("ConnectionAttempts 5\n\nHost orcz\nConnectionAttempts 3\n");
		final Host h = osc.lookup("orcz");
		assertNotNull(h);
		assertEquals(5
	}

	@Test
	public void testHostCaseInsensitive() throws Exception {
		config("hOsT orcz\nConnectionAttempts 3\n");
		final Host h = osc.lookup("orcz");
		assertNotNull(h);
		assertEquals(3
	}

	@Test
	public void testListValueSingle() throws Exception {
		config("Host orcz\nUserKnownHostsFile /foo/bar\n");
		final ConfigRepository.Config c = osc.getConfig("orcz");
		assertNotNull(c);
		assertEquals("/foo/bar"
	}

	@Test
	public void testListValueMultiple() throws Exception {
		config("Host orcz\nUserKnownHostsFile \"~/foo/ba z\" /foo/bar \n");
		final ConfigRepository.Config c = osc.getConfig("orcz");
		assertNotNull(c);
		assertArrayEquals(new Object[] { "~/foo/ba z"
				c.getValues("UserKnownHostsFile"));
	}

	@Test
	public void testRepeatedLookups() throws Exception {
		config("Host orcz\n" + "\tConnectionAttempts 5\n");
		final Host h1 = osc.lookup("orcz");
		final Host h2 = osc.lookup("orcz");
		assertNotNull(h1);
		assertSame(h1
		assertEquals(5
		assertEquals(h1.getConnectionAttempts()
		final ConfigRepository.Config c = osc.getConfig("orcz");
		assertNotNull(c);
		assertSame(c
		assertSame(c
	}

	@Test
	public void testRepeatedLookupsWithModification() throws Exception {
		config("Host orcz\n" + "\tConnectionAttempts -1\n");
		final Host h1 = osc.lookup("orcz");
		assertNotNull(h1);
		assertEquals(1
		config("Host orcz\n" + "\tConnectionAttempts 5\n");
		final Host h2 = osc.lookup("orcz");
		assertNotNull(h2);
		assertNotSame(h1
		assertEquals(5
		assertEquals(1
		assertNotSame(h1.getConfig()
	}

	@Test
	public void testIdentityFile() throws Exception {
		config("Host orcz\nIdentityFile \"~/foo/ba z\"\nIdentityFile /foo/bar");
		final Host h = osc.lookup("orcz");
		assertNotNull(h);
		File f = h.getIdentityFile();
		assertNotNull(f);
		assertEquals(new File(home
		final ConfigRepository.Config c = h.getConfig();
		assertArrayEquals(new Object[] { "~/foo/ba z"
				c.getValues("IdentityFile"));
	}

	@Test
	public void testMultiIdentityFile() throws Exception {
		config("IdentityFile \"~/foo/ba z\"\nHost orcz\nIdentityFile /foo/bar\nHOST *\nIdentityFile /foo/baz");
		final Host h = osc.lookup("orcz");
		assertNotNull(h);
		File f = h.getIdentityFile();
		assertNotNull(f);
		assertEquals(new File(home
		final ConfigRepository.Config c = h.getConfig();
		assertArrayEquals(new Object[] { "~/foo/ba z"
				c.getValues("IdentityFile"));
	}

	@Test
	public void testNegatedPattern() throws Exception {
		config("Host repo.or.cz\nIdentityFile ~/foo/bar\nHOST !*.or.cz\nIdentityFile /foo/baz");
		final Host h = osc.lookup("repo.or.cz");
		assertNotNull(h);
		assertEquals(new File(home
		assertArrayEquals(new Object[] { "~/foo/bar" }
				h.getConfig().getValues("IdentityFile"));
	}

	@Test
	public void testPattern() throws Exception {
		config("Host repo.or.cz\nIdentityFile ~/foo/bar\nHOST *.or.cz\nIdentityFile /foo/baz");
		final Host h = osc.lookup("repo.or.cz");
		assertNotNull(h);
		assertEquals(new File(home
		assertArrayEquals(new Object[] { "~/foo/bar"
				h.getConfig().getValues("IdentityFile"));
	}

	@Test
	public void testMultiHost() throws Exception {
		config("Host orcz *.or.cz\nIdentityFile ~/foo/bar\nHOST *.or.cz\nIdentityFile /foo/baz");
		final Host h1 = osc.lookup("repo.or.cz");
		assertNotNull(h1);
		assertEquals(new File(home
		assertArrayEquals(new Object[] { "~/foo/bar"
				h1.getConfig().getValues("IdentityFile"));
		final Host h2 = osc.lookup("orcz");
		assertNotNull(h2);
		assertEquals(new File(home
		assertArrayEquals(new Object[] { "~/foo/bar" }
				h2.getConfig().getValues("IdentityFile"));
	}

	@Test
	public void testEqualsSign() throws Exception {
		config("Host=orcz\n\tConnectionAttempts = 5\n\tUser=\t  foobar\t\n");
		final Host h = osc.lookup("orcz");
		assertNotNull(h);
		assertEquals(5
		assertEquals("foobar"
	}

	@Test
	public void testMissingArgument() throws Exception {
		config("Host=orcz\n\tSendEnv\nIdentityFile\t\nForwardX11\n\tUser=\t  foobar\t\n");
		final Host h = osc.lookup("orcz");
		assertNotNull(h);
		assertEquals("foobar"
		assertArrayEquals(new String[0]
		assertNull(h.getIdentityFile());
		assertNull(h.getConfig().getValue("ForwardX11"));
	}
